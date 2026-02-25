import { Link, Routes, Route, Navigate } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { useState, useEffect} from 'react'
import './App.css'

import Login from './pages/Login'
import Main from './pages/Main'
import { Provider } from 'react-redux';
import {store} from './redux/store';
import SecureRouting from './pages/SecureRouting'

import Header from './components/layouts/Header';
import Footer from './components/layouts/Footer';
import { useGetPointsQuery } from './redux/api/mainApi';

function AppContent() {

    const navigate = useNavigate();
  return (
    <div className="app">
         <Header />
         <div className="main-content">
      <Routes>

        <Route path="/" element={<Login/>} />
        <Route path="*" element={<Navigate to="/" replace />} />
         <Route path="/main" element={
                  <SecureRouting>
                    <Main />
                  </SecureRouting>
                } />
      </Routes>
    </div>
    <Footer />
    </div>
  )
}

function App() {
  return (
    <Provider store={store}>
      <AppContent />
    </Provider>
  );
}


export default App
