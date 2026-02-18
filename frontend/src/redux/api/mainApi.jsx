import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const mainApi = createApi({
  reducerPath: 'mainApi',
  baseQuery: fetchBaseQuery({ baseUrl: "/api" }),
  tagTypes: ['Points'],
  endpoints: (builder) => ({

    getPoints: builder.query({
      query: () => '/points',
      providesTags: ['Points'],
    }),

    checkPoint: builder.mutation({
      query: (pointData) => ({
        url: '/points/check',
        method: 'POST',
        body: pointData,
      }),
      invalidatesTags: ['Points'],
    }),

    login: builder.mutation({
          query: (user) => ({
            url: '/users/login',
            method: 'POST',
            body: user,
          }),
        }),

    register: builder.mutation({
          query: (user) => ({
            url: '/users/register',
            method: 'POST',
            body: user,
          }),
      }),

  }),
});

export const { useGetPointsQuery, useCheckPointMutation, useLoginMutation, useRegisterMutation } = mainApi;
export const {
  selectLogin,
  selectRegister,
} = mainApi.endpoints;