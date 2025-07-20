"use client";
import React from "react";
import cn from "classnames/bind";
import styles from "./ActionButton.module.scss";

const cx = cn.bind(styles);

type ActionButtonProps = React.PropsWithChildren<{
  className?: string;
  onClick?: (...args: never[]) => void;
  type?: "button" | "submit" | "reset";
}>;

const ActionButton = (props: ActionButtonProps) => {
  const { children, className, onClick, type = "button" } = props;
  return (
    <button className={cx("Wrapper", className)} onClick={onClick} type={type}>
      {children}
    </button>
  );
};

export default ActionButton;
