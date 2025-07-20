import axios from "axios";
import qs from "qs";
import { STORAGE_KEY } from "@/constants/storage.constant";

export const baseAjax = axios.create({
  baseURL: "http://localhost:8080",
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
  paramsSerializer(params) {
    return qs.stringify(params);
  },
});

baseAjax.interceptors.request.use(
  async function (config) {
    try {
      let token;

      if (typeof window === "undefined") {
        // Server Environment
        try {
          const headers = await import("next/headers");
          const cookieStore = await headers.cookies();
          token = cookieStore?.get(STORAGE_KEY.ACCESS_TOKEN)?.value;
        } catch (e) {
          // headers API를 사용할 수 없는 경우 (generateStaticParams 등)
          return config;
        }
      } else {
        const Cookies = (await import("js-cookie")).default;

        token = Cookies.get(STORAGE_KEY.ACCESS_TOKEN);
      }

      if (token && !config.headers.Authorization) {
        config.headers.Authorization = `Bearer ${token}`;
      }

      if (!token || config.headers["Auth"] === "false") {
        config.headers.Authorization = undefined;
        config.headers["Auth"] = undefined;
      }
    } catch (error) {
      console.error(error);
    }
    return config;
  },
  async function (err) {
    return Promise.reject(err);
  }
);

baseAjax.interceptors.response.use(
  async function (config) {
    return config;
  },
  async function (err) {
    // console.log(JSON.stringify(err.response.data, null, 2));
    throw new Error(err?.response?.data?.detail);
  }
);
