import TodoApp from "@/views/todo/components/TodoApp/TodoApp";
import React from "react";

const TodoPage = async (props: {
  searchParams: Promise<{ status: "all" | "completed" | "active" }>;
}) => {
  const { searchParams } = props;

  const { status } = await searchParams;

  return <TodoApp status={status} />;
};

export default TodoPage;
