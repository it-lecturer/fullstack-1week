import React from "react";
import cn from "classnames/bind";
import styles from "./TodoItem.module.scss";
import { FaCheck } from "react-icons/fa";
import { FaTrashAlt } from "react-icons/fa";
import ActionButton from "../ActionButton/ActionButton";

const cx = cn.bind(styles);

type TodoItemProps = {
  /** ID */
  id: string;
  /** 할일 제목 */
  title: string;
  /** 완료여부 */
  completed: boolean;
  /** 완료 여부 변화 트리거 */
  onCompletedChange?: (id: string) => void;
  /** 아이템 삭제 */
  onDeleteItem?: (id: string) => void;
  /** onEditingEnd */
  onEditingEnd?: (id: string, title: string) => void;
};

const TodoItem = (props: TodoItemProps) => {
  const {
    id,
    title,
    completed,
    onCompletedChange,
    onDeleteItem,
    onEditingEnd,
  } = props;

  const [editable, setEditable] = React.useState(false);

  const [previousValue, setPreviousValue] = React.useState("");

  React.useEffect(() => {
    // console.log(title);
    setPreviousValue(title);
  }, [title]);

  return (
    <form
      className={cx("Wrapper")}
      onSubmit={(e) => {
        e.preventDefault();
        onEditingEnd?.(id, e.currentTarget.todo.value);
        setEditable(false);
      }}
    >
      <label>
        <input
          type={"checkbox"}
          name={"todos"}
          id={id}
          defaultChecked={completed}
          hidden
          onChange={(e) => {
            onCompletedChange?.(e.target.id);
          }}
        />
        <div
          className={cx("CheckBox", {
            completed,
          })}
        >
          {completed && <FaCheck color={"var(--color-primary)"} />}
        </div>
      </label>
      <input
        type={"text"}
        className={cx("TodoTitle", {
          completed,
        })}
        id={"todo"}
        name={"todo"}
        defaultValue={props.title}
        readOnly={!editable}
        onDoubleClick={(e) => {
          setPreviousValue(e.currentTarget.value);
          setEditable(true);
        }}
        onBlur={(e) => {
          setEditable(false);
          e.currentTarget.value = previousValue;
          setPreviousValue("");
        }}
        // onKeyDown={(e) => {
        //   if (e.key === "Enter") {
        //     setEditable(false);
        //     onEditingEnd?.(id, e.currentTarget.value);
        //   }
        // }}
      />
      <ActionButton
        onClick={() => onDeleteItem?.(id)}
        className={cx("TrashButton")}
      >
        <FaTrashAlt color={"#949494"} className={cx("TrashIcon")} />
      </ActionButton>
      {/* {props.completed ? "완료" : "미완료"} */}
    </form>
  );
};

export default TodoItem;
