import {selectX, selectY, selectR, setX, setY} from '../redux/slices/pointSlice';
import { useDispatch, useSelector } from 'react-redux';
import { useGetPointsQuery } from '../redux/api/mainApi';

import styles from './Figure.module.css'

export default function Figure(){
     const X = useSelector(state => state.point.X);
     const Y = useSelector(state => state.point.Y);
     const R = useSelector(state => state.point.R);

     const xOptions = ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'];

    const dispatch = useDispatch();
    const { data: points = [], isLoading, isError } = useGetPointsQuery();

     const handleCordChange = (event) =>  {
         if (R <= 0) {
               console.log("R должен быть положительным");
               return;
             }

     const svg = event.currentTarget;
     const rect = svg.getBoundingClientRect();
     const clickX = event.clientX - rect.left;
     const clickY = event.clientY - rect.top;
     let x = (clickX - 150) / 100 * R;
     let y = ((150 - clickY) / 100 * R).toFixed(10);
        let closestX = xOptions.reduce((prev, curr) => {
                                  return Math.abs(curr - x) < Math.abs(prev - x) ? curr : prev;
                              });
     dispatch(setY(y));
     dispatch(setX(closestX));
     };
   return (
       <figure className={styles.figure}>
               <svg height="300" width="300"  className={styles.svg} xmlns="http://www.w3.org/2000/svg" onClick={handleCordChange}>

                   <line stroke="black" x1="0" x2="300" y1="150" y2="150"></line>
                   <line stroke="black" x1="150" x2="150" y1="0" y2="300"></line>
                   <polygon fill="black" points="150,0 144,15 156,15" stroke="black"></polygon>
                   <polygon fill="black" points="300,150 285,156 285,144" stroke="black"></polygon>

                   <line stroke="black" x1="200" x2="200" y1="155" y2="145"></line>
                   <line stroke="black" x1="250" x2="250" y1="155" y2="145"></line>
                   <line stroke="black" x1="50" x2="50" y1="155" y2="145"></line>
                   <line stroke="black" x1="100" x2="100" y1="155" y2="145"></line>
                   <line stroke="black" x1="145" x2="155" y1="100" y2="100"></line>
                   <line stroke="black" x1="145" x2="155" y1="50" y2="50"></line>
                   <line stroke="black" x1="145" x2="155" y1="200" y2="200"></line>
                   <line stroke="black" x1="145" x2="155" y1="250" y2="250"></line>

                   <text fill="black" x="195" y="140">R/2</text>
                   <text fill="black" x="250" y="140">R</text>
                   <text fill="black" x="40" y="140">-R</text>
                   <text fill="black" x="85" y="140">-R/2</text>
                   <text fill="black" x="160" y="55">R</text>
                   <text fill="black" x="160" y="105">R/2</text>
                   <text fill="black" x="160" y="204">-R/2</text>
                   <text fill="black" x="160" y="255">-R</text>
                   <text fill="black" x="285" y="140">X</text>
                   <text fill="black" x="160" y="15">Y</text>

                   <polygon fill="blue" fill-opacity="0.2" stroke="blue" points="150, 150 250 150 250 250 150 250"></polygon>

                   <polygon fill="blue" fill-opacity="0.2" stroke="blue" points="150,150 100,150 150,250"></polygon>

                   <path fill="blue" fill-opacity="0.2" stroke="blue" d="M 50 150 C 50 50, 150 50, 150 50 L 150 150 Z"></path>

                   <circle id="point" r="4" cx={150 + (X / R) * 100} cy={150 - (Y / R) * 100} fill="black" stroke="white"/>

                    {points.map((point, index) => (
                       <circle r="4"
                               cx={150 + (point.x / R) * 100}
                               cy={150 - (point.y / R) * 100}
                               fill={point.result == 'MISS' ? 'red' : 'green'}
                               stroke="white"
                               stroke-width="1"/>
                    ))}

               </svg>
        </figure>
       )

}