/* eslint-disable @typescript-eslint/no-empty-object-type */
declare namespace Todo {
  /** 공통 응답 타입 */
  type TodoInfo = {
    /** 할일 ID */
    id: string;
    /** 할일 제목 */
    title: string;
    /** 할일 완료 여부 */
    completed: boolean;
    /** 생성 일시 */
    createdAt: string;
    /** 수정 일시 */
    updatedAt: string;
  };

  /**
   * @method POST
   * @path /api/todos
   */
  namespace Post {
    type Path = {};
    type Params = {};
    type Body = Pick<TodoInfo, "title">;

    type Request = {
      path?: Path;
      params?: Params;
      body: Body;
    };

    type Response = TodoInfo;
  }

  /**
   * @method GET
   * @path /api/todos
   */
  namespace GetList {
    type Path = {};
    type Params = {};
    type Body = {};

    type Request = {
      path?: Path;
      params?: Params;
      body?: Body;
    };

    type Response = TodoInfo;
  }

  /**
   * @method GET
   * @path /api/todos/:id
   */
  namespace GetOne {
    type Path = {
      id: string;
    };
    type Params = {};
    type Body = {};

    type Request = {
      path: Path;
      params?: Params;
      body?: Body;
    };

    type Response = TodoInfo;
  }

  /**
   * @method PUT
   * @path /api/todos/:id
   */
  namespace Put {
    type Path = {
      id: string;
    };
    type Params = {};
    type Body = Partial<Pick<TodoInfo, "title" | "completed">>;

    type Request = {
      path: Path;
      params?: Params;
      body: Body;
    };

    type Response = void;
  }

  /**
   * @method DELETE
   * @path /api/todos/:id
   */
  namespace Delete {
    type Path = {
      id: string;
    };
    type Params = {};
    type Body = {};

    type Request = {
      path: Path;
      params?: Params;
      body?: Body;
    };

    type Response = void;
  }

  namespace BulkDelete {
    type Path = {};
    type Params = {};
    type Body = {
      ids: string[];
    };

    type Request = {
      path?: Path;
      params?: Params;
      body: Body;
    };

    type Response = void;
  }
}
