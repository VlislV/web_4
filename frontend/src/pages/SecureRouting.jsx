import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

const SecureRouting = ({ children }) => {
  const { isAuthenticated } = useSelector((state) => state.user);

  if (!isAuthenticated) {
    return <Navigate to="/"/>;
  }

  return children;
};

export default SecureRouting;