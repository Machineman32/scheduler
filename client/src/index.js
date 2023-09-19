import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import Home from './components/Home';
import Calendar from './components/Calendar'
import Navbar from './components/Navbar'

import {store} from './store'
import {Provider} from "react-redux";

import {createBrowserRouter, RouterProvider, Router, Link, Outlet} from "react-router-dom";
import TaskManager from "./components/TaskManager";

const AppLayout = () => {
    return (
        <>
            <Navbar/>
            <Outlet/>
        </>
    )
}

const router = createBrowserRouter([
    {
        element: <AppLayout/>,
        children: [
            {
                path: "/",
                element: <Home/>,
            },
            {
                path: "/task-manager",
                element: <TaskManager/>
            },
            {
                path: "calendar",
                element: <Calendar/>,
            }
        ]
    }
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Provider store={store}>
        <RouterProvider router={router}/>
    </Provider>
);

