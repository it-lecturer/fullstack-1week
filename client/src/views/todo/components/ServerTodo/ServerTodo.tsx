"use client";
import React from "react";
import styles from "./ServerTodo.module.scss";
import cn from "classnames/bind";
import TodoInput from "../TodoInput/TodoInput";
import TodoList from "../TodoList/TodoList";
import TodoFooter from "../TodoFooter/TodoFooter";
import * as TodoMutation from "@/api/fetch/mutation/todo/todo";

const cx = cn.bind(styles);

type ServerTodoProps = {
  status?: "all" | "completed" | "active";
  todos?: Todo.GetList.Response;
};

const ServerTodo = (props: ServerTodoProps) => {
  const { status = "all", todos } = props;

  const addTodo = (title: string) => {
    TodoMutation.Post({
      body: {
        title,
      },
    });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const isEmpty = !e.currentTarget?.todo?.value?.trim()?.length;

    if (!isEmpty) {
      addTodo(e.currentTarget.todo.value);
    }

    e.currentTarget.todo.value = "";
  };

  const handleToggleCompleted = (id: string) => {
    TodoMutation.Put({
      path: {
        id,
      },
      body: {
        completed: !todos?.find((todo) => todo.id === id)?.completed,
      },
    });
  };

  const handleDeleteAllCompleted = () => {
    TodoMutation.BulkDelete({
      body: {
        ids:
          todos?.filter((todo) => todo.completed).map((todo) => todo.id) || [],
      },
    });
  };

  const handleDeleteItem = React.useCallback((id: string) => {
    TodoMutation.Delete({
      path: {
        id,
      },
    });
  }, []);

  const handleAllToggleCompleted = React.useCallback(() => {}, []);

  const unCompletedCount = React.useMemo(
    () => todos?.filter((todo) => !todo.completed).length || 0,
    [todos]
  );

  const todoList = React.useMemo(() => {
    switch (status) {
      case "all":
        return todos;
      case "completed":
        return todos?.filter((todo) => todo.completed) || [];
      case "active":
        return todos?.filter((todo) => !todo.completed) || [];
      default:
        return todos;
    }
  }, [status, todos]);

  return (
    <div className={cx("PageWrpper")}>
      <h1 className={cx("Title")}>Todo List</h1>
      <div className={cx("Wrapper")}>
        <TodoInput
          onSubmit={handleSubmit}
          handleArrowClick={handleAllToggleCompleted}
        />
        <TodoList
          todos={todoList ?? []}
          onToggleCompleted={handleToggleCompleted}
          onDeleteItem={handleDeleteItem}
        />
        {
          <TodoFooter
            remainCount={unCompletedCount}
            status={status || "all"}
            handleDeleteAllCompleted={handleDeleteAllCompleted}
          />
        }
      </div>
    </div>
  );
};

export default ServerTodo;
