import React, {useState} from "react";
import {NavLink, Link} from "react-router-dom";

import "../style/Navbar.css"

import {FaBars, FaTimes} from "react-icons/fa";
import {RiTodoLine} from "react-icons/ri"
import {IconContext} from "react-icons";

const Navbar = () => {

    const [active, setActive] = useState(false);
    const handleClick = () => setActive(!active);

    return (
        <IconContext.Provider value={{color: "white"}}>
            <nav className="navbar">
                <div className="navbar-wrapper wrapper">
                    <Link to="/" className="navbar-logo">
                        <RiTodoLine className="navbar-icon"/>
                        Todos
                    </Link>
                    <div className="menu-icon" onClick={handleClick}>
                        {active ? <FaTimes/> : <FaBars/>}
                    </div>
                    <ul className={active ? "nav-menu active" : "nav-menu"}>
                        <li className="nav-item">
                            <NavLink to="/" className={({isActive}) => "nav-links" + (isActive ? " active active-link" : "")}>
                                Home
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink to="/calendar" className={({isActive}) => "nav-links" + (isActive ? " active active-link" : "")}>
                                Calendar
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink to={"/task-manager"} className={({isActive}) => "nav-links" + (isActive ? " active active-link" : "")}>
                                Task manager
                            </NavLink>
                        </li>
                    </ul>
                </div>
            </nav>
        </IconContext.Provider>
    )
}

export default Navbar