import { AxiosInstance } from "axios";

export const TODO_ROUTES = {
  /** 할일 추가 */
  POST: "/api/todos",
  /** 할일 목록 조회 */
  GET_LIST: "/api/todos",
  /** 할일 상세 조회 */
  GET_ONE: "/api/todos/:id",
  /** 할일 수정 */
  PUT: "/api/todos/:id",
  /** 할일 삭제 */
  DELETE: "/api/todos/:id",
  /** 할일 일괄 삭제 */
  BULK_DELETE: "/api/todos/bulk",
} as const;

export class TodoService {
  constructor(private _ajax: AxiosInstance) {}

  async post(
    req: Todo.Post.Request
  ): Promise<ServiceResponse<Todo.Post.Response>> {
    console.log(req);
    try {
      const { data } = await this._ajax.post<Todo.Post.Response>(
        TODO_ROUTES.POST,
        req.body
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
        req.body
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
        TODO_ROUTES.DELETE.replace(":id", req.path.id)
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
        { data: req.body }
      );

      return { data, error: null };
    } catch (error) {
      return { data: null, error };
    }
  }
}
