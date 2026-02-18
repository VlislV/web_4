import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { mainApi, useLoginMutation } from '../api/mainApi';

const { selectLogin, selectRegister } = mainApi.endpoints

const initialState = {
  username: null,
  isAuthenticated: false,
  userError: null,
};

const userSlice = createSlice({
  name: 'user',
  initialState,
    reducers: {
        logout: (state) => {
            state.username = null;
            state.isAuthenticated = false;
            state.userError = null;
        }
    },
    extraReducers: (builder) => {
        builder
         .addMatcher(
                mainApi.endpoints.login.matchFulfilled,
                (state, {payload}) => {
                      state.username = payload.username;
                      state.isAuthenticated = true;
                      state.userError = null;
                }
              )
          .addMatcher(
                  mainApi.endpoints.login.matchRejected,
                  (state, { payload, error }) => {
                      state.isAuthenticated = false;
                      state.username = null;
                      const statusCode = payload?.status || error?.status || error?.originalStatus;
                      switch(statusCode) {
                              case 401:
                                state.userError = 'Неверный пароль';
                                break;
                              case 500:
                                state.userError = 'Ошибка сервера';
                                break;
                              default:
                                state.userError = 'Ошибка входа';
                            }
                  }
              )
           .addMatcher(
                  mainApi.endpoints.register.matchFulfilled,
                  (state, { payload }) => {
                    state.username = payload.username;
                    state.isAuthenticated = true;
                    state.userError = "";
                  }
                )
                .addMatcher(
                  mainApi.endpoints.register.matchRejected,
                  (state, { payload, error }) => {
                    state.userError = payload?.message || error?.message || 'Ошибка регистрации';
                  }
                );
            }
});

export const { logout } = userSlice.actions;
export default userSlice.reducer;