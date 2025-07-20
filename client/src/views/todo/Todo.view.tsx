import React from "react";
import TodoApp from "./components/TodoApp/TodoApp";

type TodoViewProps = {
  status?: "all" | "completed" | "active";
};

const TodoView = (props: TodoViewProps) => {
  const { status = "all" } = props;
  return <TodoApp status={status} />;
};

export default TodoView;
