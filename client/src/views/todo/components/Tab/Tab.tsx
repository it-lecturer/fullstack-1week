import React from "react";
import cn from "classnames/bind";
import styles from "./Tab.module.scss";

const cx = cn.bind(styles);

type TabItem = {
  id: string;
  title: string;
  onClick?: (...args: any) => void;
};

type TabProps = {
  list?: Array<TabItem>;
  activeId?: string;
  className?: string;
};

const Tab = (props: TabProps) => {
  const { list = [], activeId, className } = props;

  return (
    <div className={cx("Wrapper", className)}>
      {list.map((item) => {
        return (
          <button
            key={item.id}
            onClick={item.onClick}
            type={"button"}
            className={cx("TabItem", { active: item.id === activeId })}
          >
            {item.title}
          </button>
        );
      })}
    </div>
  );
};

export default Tab;
