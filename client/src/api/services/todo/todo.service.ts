import { AxiosInstance } from "axios";

export const TODO_ROUTES = {
  POST: "/api/todos",
  GET_LIST: "/api/todos",
  GET_ONE: "/api/todos/:id",
  PUT: "/api/todos/:id",
  DELETE: "/api/todos/:id",
  BULK_DELETE: "/api/todos/bulk",
} as const;

export class TodoService {
  constructor(private _ajax: AxiosInstance) {}

  async post(
    req: Todo.Post.Request
  ): Promise<ServiceResponse<Todo.Post.Response>> {
    try {
      const { data } = await this._ajax.post<Todo.Post.Response>(
        TODO_ROUTES.POST,
        req
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }

  async getList(
    req: Todo.GetList.Request
  ): Promise<ServiceResponse<Todo.GetList.Response>> {
    try {
      const { data } = await this._ajax.get<Todo.GetList.Response>(
        TODO_ROUTES.GET_LIST,
        req
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }

  async getOne(
    req: Todo.GetOne.Request
  ): Promise<ServiceResponse<Todo.GetOne.Response>> {
    try {
      const { data } = await this._ajax.get<Todo.GetOne.Response>(
        TODO_ROUTES.GET_ONE.replace(":id", req.path.id),
        req
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }

  async put(
    req: Todo.Put.Request
  ): Promise<ServiceResponse<Todo.Put.Response>> {
    try {
      const { data } = await this._ajax.put<Todo.Put.Response>(
        TODO_ROUTES.PUT.replace(":id", req.path.id),
        req
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }

  async delete(
    req: Todo.Delete.Request
  ): Promise<ServiceResponse<Todo.Delete.Response>> {
    try {
      const { data } = await this._ajax.delete<Todo.Delete.Response>(
        TODO_ROUTES.DELETE.replace(":id", req.path.id),
        req
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }

  async bulkDelete(
    req: Todo.BulkDelete.Request
  ): Promise<ServiceResponse<Todo.BulkDelete.Response>> {
    try {
      const { data } = await this._ajax.delete<Todo.BulkDelete.Response>(
        TODO_ROUTES.BULK_DELETE,
        req
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }
}
