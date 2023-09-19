import "../App.css"
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import { fetchTodosAsync, createTodoAsync, updateTodoAsync, deleteTodoAsync } from '../redux/todosSlice';
import {Todo} from "./Todo";

const Home = () => {
    const dispatch = useDispatch();
    const [newTodoText, setNewTodoText] = useState('');
    const todos = useSelector((state) => state.todos.todos);
    const loading = useSelector((state) => state.request.loading);
    const error = useSelector((state) => state.request.error);

    useEffect(() => {
        dispatch(fetchTodosAsync());
    }, [dispatch]);

    const handleCreateTodo = () => {
        dispatch(createTodoAsync({ text: newTodoText }));
        setNewTodoText('');
    };

    const handleUpdateTodo = (todoId, newText) => {
        dispatch(updateTodoAsync({ id: todoId, text: newText }));   //full todo object
    };

    const handleDeleteTodo = (todoId) => {
        dispatch(deleteTodoAsync(todoId));
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }
    //The list of tasks here
    //Modifying the tasks here

    return (
        <div className="home-page">
            {todos.map((todo) => (
                <Todo
                    task={todo.task}
                    category={todo.category}
                    completed={todo.completed}
                    deadline={todo.deadline}
                />
            ))}
        </div>
    );
};

export default Home