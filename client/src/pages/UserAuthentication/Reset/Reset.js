import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import './Reset.css';
import { useNavigate } from 'react-router-dom';

export function Reset() {
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  const [error, setError] = useState(false);
  const [passErrorMsg, setPassErrorMsg] = useState('');
  const [conPassErrorMsg, setconPassErrorMsg] = useState('');
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}$/;
  const location = useLocation();
  let [resetCode, setResetCode] = useState(location.state?.code || null);
  const resetEmail = location.state?.email;
  const navigate = useNavigate();

  const onNewPasswordBlur = () => {
    if (!passwordRegex.test(newPassword) && newPassword !== "") {
      setError(true);
      setPassErrorMsg("Password must contain 1 uppercase<br>Must contain lowercase <br> Must contain special charcter <br> minimum length of 8 characters");
    } else {
      setError(false);
      setPassErrorMsg('');
    }
  };

  const onConfirmPasswordBlur = () => {
    if (!passwordRegex.test(confirmPassword) && confirmPassword !== "") {
      setError(true);
      setconPassErrorMsg("Password must contain 1 uppercase<br>Must contain lowercase <br> Must contain special charcter <br> minimum length of 8 characters");
    } else {
      setError(false);
      setconPassErrorMsg('');
    }
  };

  const onSubmit = () => {
    if (error === true || newPassword === "" || confirmPassword === "")
      alert("Please enter the correct password");
    else if (newPassword !== confirmPassword)
      alert("Confirm password does not match with the new password");
    else if (verificationCode.length !== 6)
      alert("Please enter the valid 6-letter alphanumeric code");
    else if (resetCode !== verificationCode)
      alert("Invalid Verification Code");
    else {
      const resetData = {
        userId: localStorage.getItem('userID'),
        email: resetEmail,
        password: newPassword,
      };

      fetch('https://commune-dev-csci5308-server.onrender.com/users/resetPassword', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(resetData),
      })
        .then((response) => {
          if (response.status === 202) {
            alert('Password changed successfully.\nRedirecting to the login page...');
            setNewPassword('');
            setConfirmPassword('');
            setVerificationCode('');
            window.location.href = '/login';
          } else {
            alert('Failed to reset the password. Please try again later.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('An error occurred during password reset. Please try again later.');
        });
    }
  };

  const resendCode = (e) => {
    e.preventDefault();
    console.log(resetEmail);
    if (resetEmail === "" || resetEmail === null) {
      console.log(resetEmail);
      alert("An error occurred during the process. Please try again later.");
    } else {
      fetch(`https://commune-dev-csci5308-server.onrender.com/users/forgotPassword?email=${resetEmail}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: '',
      })
        .then((response) => response.text())
        .then((code) => {
          console.log(resetCode);
          setResetCode(code);
          console.log(resetCode);
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('An error occurred during the process. Please try again later.');
        });
      alert("Code has been sent to the registered Email ID");
    }
  };

  const goBackToLogin = (e) => {
    navigate('/login');
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="reset-form-container">
            <form>
              <h3 className="text-center">Reset Your Password</h3>
              <div className="form-group">
                <input
                  className="form-control"
                  type="password"
                  placeholder="New Password"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  onBlur={onNewPasswordBlur}
                />
                <i dangerouslySetInnerHTML={{ __html: passErrorMsg }} />
              </div>
              <div className="form-group mt-2">
                <input
                  className="form-control"
                  type="password"
                  placeholder="Confirm Password"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  onBlur={onConfirmPasswordBlur}
                />
                <i dangerouslySetInnerHTML={{ __html: conPassErrorMsg }} />
              </div>
              <div className="form-group d-flex align-items-center justify-content-between mt-2">
                <input
                  className="form-control"
                  type="text"
                  placeholder="Verification Code"
                  value={verificationCode}
                  onChange={(e) => setVerificationCode(e.target.value)}
                />
                <button
                  className="btn-reset"
                  onClick={resendCode}
                >
                  Resend
                </button>
              </div>
              <div className="form-group d-flex align-items-center justify-content-between mt-2">
                <button
                  type="button"
                  className="btn-reset"
                  onClick={onSubmit}
                >
                  Submit
                </button>
                <span
                  className="text-link"
                  onClick={goBackToLogin}
                >
                  Go Back to login
                </span>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
