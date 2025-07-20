import React from "react";
import ServerTodo from "./components/ServerTodo/ServerTodo";

type TodoViewProps = {
  status?: "all" | "completed" | "active";
  todos?: Todo.GetList.Response;
};

const TodoView = (props: TodoViewProps) => {
  const { status = "all", todos } = props;
  return <ServerTodo todos={todos} status={status} />;
};

export default TodoView;
