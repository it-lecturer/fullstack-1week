import { TodoService } from "./services/todo/todo.service";
import { baseAjax } from "./instance";

export const todoService = new TodoService(baseAjax);
