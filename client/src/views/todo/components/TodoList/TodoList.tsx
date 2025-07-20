"use client";
import React from "react";
import cn from "classnames/bind";
import styles from "./TodoList.module.scss";
import TodoItem from "../TodoItem/TodoItem";

const cx = cn.bind(styles);

type TodoListProps = {
  todos: Array<ITodo>;
  onToggleCompleted?: (id: string) => void;
  onDeleteItem?: (id: string) => void;
  onEditingEnd?: (id: string, title: string) => void;
};

const TodoList = (props: TodoListProps) => {
  const { todos, onToggleCompleted, onDeleteItem, onEditingEnd } = props;

  return (
    <div className={cx("Wrapper")}>
      {todos.map((todo) => {
        return (
          <TodoItem
            key={todo.id}
            title={todo.title}
            completed={todo.completed}
            id={todo.id}
            onCompletedChange={onToggleCompleted}
            onDeleteItem={onDeleteItem}
            onEditingEnd={onEditingEnd}
          />
        );
      })}
    </div>
  );
};

export default TodoList;
