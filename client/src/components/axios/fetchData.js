import axios from "axios";

export const fetchData = async () => axios.get("http://localhost:8080/api/v1/todos")
    .then((res) => {
        return res.data
    })
    .catch((err) => {
        console.log(err)
    });