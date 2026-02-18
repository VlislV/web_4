import { Link, Routes, Route, Navigate } from 'react-router-dom'
import { useState } from 'react'
import './App.css'

import Login from './pages/Login'
import Main from './pages/Main'
import { Provider } from 'react-redux';
import {store} from './redux/store';
import SecureRouting from './pages/SecureRouting'

import Header from './components/layouts/Header';
import Footer from './components/layouts/Footer';

function App() {
  return (
    <div className="app">
         <Header />
         <div className="main-content">
    <Provider store={store}>
      <Routes>
        <Route path="/" element={<Login/>} />
        <Route path="*" element={<Navigate to="/" replace />} />
         <Route path="/main" element={
                  <SecureRouting>
                    <Main />
                  </SecureRouting>
                } />
      </Routes>
    </Provider>
    </div>
    <Footer />
    </div>
  )
}


export default App
