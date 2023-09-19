import React from "react";
import TodoForm from "./TodoForm";
export const Todo = ({id, task, category, completed, deadline}) => {

    const isCompleted = (completed) => {
        return completed ? "Yes" : "Not yet"
    }

    return (
        <div className="todo-wrapper">
            <li key={1}>
                <div className="task-content">
                    <h3>{task}</h3>
                    <p>Category: {category}</p>
                    <p>Completed: {isCompleted()}</p>
                    <p>Deadline: {deadline}</p>
                </div>
                <div className="task-options">
                    <button>Edit Task</button>
                    <button>Mark as completed</button>
                </div>
            </li>
        </div>
    )
}