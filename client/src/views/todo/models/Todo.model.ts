import { v4 as uuid } from "uuid";

export class Todo implements ITodo {
  id: string = `todo-${uuid()}`;
  title: string;
  completed: boolean = false;
  createdAt: string = new Date().toISOString();
  updatedAt: string = new Date().toISOString();

  constructor(params: Pick<Todo, "title">) {
    this.title = params.title;
  }
}
