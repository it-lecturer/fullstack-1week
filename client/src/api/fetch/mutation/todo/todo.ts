"use server";

import { todoService } from "@/api";
import { revalidatePath } from "next/cache";

export const Delete = async (req: Todo.Delete.Request) => {
  try {
    const data = await todoService.delete(req);

    revalidatePath("/todos");
    return data;
  } catch (error) {
    console.error(error);
  }
};

export const Post = async (req: Todo.Post.Request) => {
  try {
    const data = await todoService.post(req);

    revalidatePath("/todos");

    console.log(data);
    return data;
  } catch (error) {
    console.error(error);
  }
};

export const Put = async (req: Todo.Put.Request) => {
  try {
    await todoService.put(req);

    revalidatePath("/todos");
  } catch (error) {
    console.error(error);
  }
};

export const BulkDelete = async (req: Todo.BulkDelete.Request) => {
  try {
    await todoService.bulkDelete(req);

    revalidatePath("/todos");
  } catch (error) {
    console.error(error);
  }
};
