import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const fetchTodosAsync = createAsyncThunk(
    'todos/fetchTodosAsync',
    async () => {
        const response = await axios.get('http://localhost:8080/api/v1/todos');
        return response.data;
    }
);

export const createTodoAsync = createAsyncThunk(
    'todos/createTodoAsync',
    async (newTodo) => {
        const response = await axios.post('http://localhost:8080/api/v1/todos', newTodo);
        return response.data;
    }
);

export const updateTodoAsync = createAsyncThunk(
    'todos/updateTodoAsync',
    async (updatedTodo) => {
        const response = await axios.put(
            `http://localhost:8080/api/v1/todos/${updatedTodo.id}`,
            updatedTodo
        );
        return response.data;
    }
);

export const deleteTodoAsync = createAsyncThunk(
    'todos/deleteTodoAsync',
    async (todoId) => {
        await axios.delete(`https://localhost:8080/api/v1/todos/${todoId}`);
        return todoId;
    }
);

const initialState = {
    todos: [],
};

const todosSlice = createSlice({
    name: 'todos',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchTodosAsync.pending, (state) => {                      //Pending request
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchTodosAsync.rejected, (state, action) => {          //Rejected request
                state.error = action.error.message;
                state.loading = false;
            })
            .addCase(fetchTodosAsync.fulfilled, (state, action) => {         //200 OK
                state.todos = action.payload;
                state.loading = false;
            })
            .addCase(createTodoAsync.fulfilled, (state, action) => {         //POST
                state.todos.push(action.payload)
            })
            .addCase(updateTodoAsync.fulfilled, (state, action) => {         //UPDATE
                const updatedTodo = action.payload;
                const index = state.todos.findIndex((todo) => todo.id === updatedTodo.id);
                if (index !== -1) {
                    state.todos[index] = updatedTodo;
                }
            })
            .addCase(deleteTodoAsync.fulfilled, (state, action) => {         //DELET
                const todoId = action.payload;
                state.todos = state.todos.filter((todo) => todo.id !== todoId);
            });
    },
});

export default todosSlice.reducer;