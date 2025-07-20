import { LOCALE_URL_REGEX } from "@/constants/app.constant";
import { compile } from "path-to-regexp";

export const parsePath = (path: string) => {
  /** Locale */
  const { locale } = LOCALE_URL_REGEX.exec(path)?.groups || {};

  /** Locale 제외한 Path */
  const pathname = path.replace(LOCALE_URL_REGEX, "$<path>");

  return {
    locale,
    pathname,
  };
};

/** get Pathname */
export const getParamPath = (
  params: { [key: string]: never | string | number | boolean | undefined },
  pathname: string
) => {
  const paramMap = new Map(
    Object.entries(params).map(([key, value]) => [value, key])
  );

  const pathArray = pathname.split("/").map((path) => {
    const paramKey = paramMap.get(path);

    return `${paramKey ? `:${paramKey}` : path}`;
  });

  return pathArray.join("/").replace(/^\/(:locale)/, "") || "/";
};

export const pathToUrl = (path: string, params?: Record<string, string>) => {
  const toPath = compile(path);
  return toPath(params);
};
