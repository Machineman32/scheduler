import {configureStore} from "@reduxjs/toolkit";
import thunk from "redux-thunk";
import todosReducer from "./redux/todosSlice"
import requestReducer from "./redux/requestSlice"

export const store = configureStore({
    reducer: {
        request: requestReducer,
        todos: todosReducer,
    },
    middleware: [thunk],
})