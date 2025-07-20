"use client";
import React, { Profiler } from "react";
import styles from "./TodoApp.module.scss";
import cn from "classnames/bind";
import TodoInput from "../TodoInput/TodoInput";
import TodoList from "../TodoList/TodoList";
import TodoFooter from "../TodoFooter/TodoFooter";
import { Todo } from "../../models/Todo.model";

const cx = cn.bind(styles);

const MemoTodoInput = React.memo(TodoInput);
const MemoTodoList = React.memo(TodoList);
const MemoTodoFooter = React.memo(TodoFooter);

type TodoAppProps = {
  status?: "all" | "completed" | "active";
};

const TodoApp = (props: TodoAppProps) => {
  const { status = "all" } = props;
  const [todos, setTodos] = React.useState<Array<ITodo>>([]);

  const addTodo = React.useCallback((title: string) => {
    setTodos((prev) => {
      return [
        ...prev,
        new Todo({
          title,
        }),
      ];
    });
  }, []);

  const handleSubmit = React.useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();

      const isEmpty = !e.currentTarget?.todo?.value?.trim()?.length;

      if (!isEmpty) {
        addTodo(e.currentTarget.todo.value);
      }

      e.currentTarget.todo.value = "";
    },
    [addTodo]
  );

  const handleToggleCompleted = React.useCallback((id: string) => {
    setTodos((prevTodoList) => {
      return prevTodoList.map((prevTodoItem) => {
        return {
          ...prevTodoItem,
          completed:
            prevTodoItem.id !== id
              ? prevTodoItem.completed
              : !prevTodoItem.completed,
        };
      });
    });
  }, []);

  const handleDeleteAllCompleted = React.useCallback(() => {
    setTodos((prevTodoList) => {
      return prevTodoList.filter((todo) => !todo.completed);
    });
    // setTodos(todos.filter((todo) => !todo.completed));
  }, []);

  const handleDeleteItem = React.useCallback((id: string) => {
    setTodos((prevTodoList) => {
      return prevTodoList.filter((todo) => todo.id !== id);
    });
    // setTodos(todos.filter((todo) => todo.id !== id));
  }, []);

  const handleAllToggleCompleted = React.useCallback(() => {
    setTodos((prevTodoList) => {
      const isEveryChecked = prevTodoList.every((todo) => todo.completed);

      return prevTodoList.map((prevTodoItem) => {
        return {
          ...prevTodoItem,
          completed: isEveryChecked ? false : true,
        };
      });
    });
  }, []);

  const unCompletedCount = React.useMemo(
    () => todos.filter((todo) => !todo.completed).length,
    [todos]
  );

  const todoList = React.useMemo(() => {
    switch (status) {
      case "all":
        return todos;
      case "completed":
        return todos.filter((todo) => todo.completed);
      case "active":
        return todos.filter((todo) => !todo.completed);
      default:
        return todos;
    }
  }, [status, todos]);

  const handleEditingEnd = React.useCallback((id: string, title: string) => {
    setTodos((prevTodoList) => {
      return prevTodoList.map((prevTodoItem) => {
        return {
          ...prevTodoItem,
          title: prevTodoItem.id === id ? title : prevTodoItem.title,
        };
      });
    });
  }, []);

  return (
    <Profiler
      id="TodoApp"
      onRender={(
        id,
        phase,
        actualDuration,
        baseDuration,
        startTime,
        commitTime
      ) => {
        // console.table({
        //   id,
        //   phase,
        //   actualDuration,
        //   baseDuration,
        //   startTime,
        //   commitTime,
        // });
      }}
    >
      <div className={cx("PageWrpper")}>
        <h1 className={cx("Title")}>Todo List</h1>
        <div className={cx("Wrapper")}>
          <MemoTodoInput
            onSubmit={handleSubmit}
            handleArrowClick={handleAllToggleCompleted}
          />
          <MemoTodoList
            todos={todoList}
            onToggleCompleted={handleToggleCompleted}
            onDeleteItem={handleDeleteItem}
            onEditingEnd={handleEditingEnd}
          />
          {
            <MemoTodoFooter
              remainCount={unCompletedCount}
              status={status || "all"}
              handleDeleteAllCompleted={handleDeleteAllCompleted}
            />
          }
        </div>
      </div>
    </Profiler>
  );
};

export default TodoApp;
