import React from 'react'
import "../style/TodoForm.css"

const TodoForm = () => {
    return (
        <form className="todo-form">
            <div className="task-wrapper">
                <p>Please write the task</p>
                <input value="Here"/>
            </div>
            <div className="deadline-wrapper">
                <p>Please choose a deadline date</p>
                <input type={"date"}/>
            </div>
            <div>
                <p>Please select a category</p>
                <select id="categories" name="categories">
                    <option value="reading">Reading</option>
                    <option value="working">Working</option>
                    <option value="fun">Fun</option>
                    <option value="sports">Sports</option>
                </select>
            </div>
            <button>ADD</button>
        </form>
    )
}

export default TodoForm