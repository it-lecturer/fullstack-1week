"use client";
import React from "react";
import cn from "classnames/bind";
import styles from "./TodoFooter.module.scss";
import Tab from "../Tab/Tab";
import ActionButton from "../ActionButton/ActionButton";
import { useRouter } from "next/navigation";

const cx = cn.bind(styles);

type TodoFooterProps = {
  // todos?: Array<ITodo>;
  remainCount?: number;
  status?: string;
  handleDeleteAllCompleted?: () => void;
};

const tabs = [
  {
    title: "All",
    id: "all",
  },
  {
    title: "Active",
    id: "active",
  },
  {
    title: "Completed",
    id: "completed",
  },
];

const TodoFooter = (props: TodoFooterProps) => {
  const { remainCount = 0, status = "all", handleDeleteAllCompleted } = props;

  const router = useRouter();
  return (
    <div className={cx("Wrapper")}>
      <span className={cx("RemainCount")}>{remainCount} items left</span>
      <Tab
        list={tabs.map((tab) => {
          return {
            ...tab,
            onClick: () => {
              router.push("?status=" + tab.id);
            },
          };
        })}
        className={cx("FooterTab")}
        activeId={status}
      />
      <ActionButton
        className={cx("RemoveButton")}
        onClick={handleDeleteAllCompleted}
      >
        Clear Completed
      </ActionButton>
    </div>
  );
};

export default TodoFooter;
