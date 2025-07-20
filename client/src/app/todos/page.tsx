import TodoView from "@/views/todo/Todo.view";
import React from "react";

const TodoPage = async (props: {
  searchParams: Promise<{ status: "all" | "completed" | "active" }>;
}) => {
  const { searchParams } = props;

  const { status } = await searchParams;

  return <TodoView status={status} />;
};

export default TodoPage;
