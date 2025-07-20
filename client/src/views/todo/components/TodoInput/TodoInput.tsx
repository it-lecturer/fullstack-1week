"use client";
import React from "react";
import cn from "classnames/bind";
import styles from "./TodoInput.module.scss";
import ActionButton from "../ActionButton/ActionButton";
import { FaChevronDown } from "react-icons/fa6";

const cx = cn.bind(styles);

type TodoInputProps = {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
  // handleAllToggleCompleted?: () => void;
  handleArrowClick?: () => void;
};

const TodoInput = (props: TodoInputProps) => {
  const { onSubmit, handleArrowClick } = props;
  const [focused, setFocused] = React.useState(false);

  return (
    <form
      onSubmit={onSubmit}
      className={cx("Wrapper", {
        focused,
      })}
    >
      <ActionButton
        className={cx("TodoInputButton")}
        onClick={handleArrowClick}
      >
        <FaChevronDown />
      </ActionButton>

      <input
        type={"text"}
        className={cx("Input")}
        name={"todo"}
        id={"todo"}
        onFocus={() => {
          setFocused(true);
        }}
        onBlur={() => {
          setFocused(false);
        }}
      />
    </form>
  );
};

export default TodoInput;
