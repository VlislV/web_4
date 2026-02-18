import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  X: 0,
  Y: '',
  R: 1,
  pointError: null,
};

const pointSlice = createSlice({
  name: 'point',
  initialState,
  reducers: {
    resetCoordinates: () => initialState,
    setX: (state, action) => {
        state.X = action.payload;
    },
    setY: (state, action) => {
        state.Y = action.payload;
        if(!isNaN(action.payload) && action.payload <= 3 && action.payload >= -3){
            state.pointError = null;
        }else{
            console.warn("INVALID Y -> Y change");
            state.pointError = "Не корректное значение Y";
        }
    },
    setR: (state, action) => {
        if(action.payload <= 0){
            console.warn("Отрицательный радиус или 0 -> R no change");
            state.pointError = "Отрицательный радиус или 0";
        }else{
            state.R = action.payload;
            state.pointError = null;
        }
    },
  },

});

export const {resetCoordinates, setX, setY, setR} = pointSlice.actions;
export const selectX = (state) => state.point.X;
export const selectY = (state) => state.point.Y;
export const selectR = (state) => state.point.R;
export default pointSlice.reducer;