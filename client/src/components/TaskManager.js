import React from "react";
import TodoForm from "./TodoForm";
import {createTodoAsync} from "../redux/todosSlice";

//Adding the tasks here
const TaskManager = () => {

    const handleCreateTodo = () => {
        dispatch(createTodoAsync({ text: newTodoText }));
        setNewTodoText('');
    };

    return (
        <div>
            <TodoForm

            />
        </div>
    )
}

export default TaskManager