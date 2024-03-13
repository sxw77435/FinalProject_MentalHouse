
    Kakao.init('8599dbb702c380d1faa66d7af39a7430'); // 발급받은 키 중 JavaScript 키를 사용

    function kakaoLogout() {
        console.log("Attempting to logout from Kakao" + Kakao.Auth.getAccessToken());

        // Check if the user is logged in to Kakao
        if (Kakao.Auth.getAccessToken()) {
            Kakao.API.request({
            url: '/v1/user/unlink',
            success: function(response) {
            console.log('Kakao logout successful', response);
            window.location.href = "/logout/logoutpage"; // Redirect to your logout page
            },
            fail: function(error) {
            console.error('Kakao logout failed', error);
            }
            })
            Kakao.Auth.setAccessToken(undefined); // Clear Kakao access token
        }else {
            window.location.href = "/logout/logoutpage"; // Redirect to your logout page
        }
    }