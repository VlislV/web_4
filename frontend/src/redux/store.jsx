import { configureStore } from '@reduxjs/toolkit'
import { setupListeners } from '@reduxjs/toolkit/query'
import {mainApi} from './api/mainApi'
import pointReducer from '../redux/slices/pointSlice';
import userReducer from '../redux/slices/userSlice';

export const store = configureStore({
  reducer: {
      [mainApi.reducerPath]: mainApi.reducer,
      user: userReducer,
      point: pointReducer,
      },
   middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware().concat(mainApi.middleware),
})
setupListeners(store.dispatch)