import { createAction } from "typesafe-actions";
import { create } from 'domain';
import axios from 'axios';

export const getCurrentrace = (data) => {
    return (dispatch) => {
        if (data && data.eventType === 'CURRENT_RACE_STATUS') {
            axios.get(`http://localhost:10580/currentrace`)
            .then(resp => {
                dispatch({type: 'GetCurrentrace', payload: resp });
            })
            .catch(err => {
                console.log("Error")
            });
        }       
    };
};