document.addEventListener("DOMContentLoaded", () => {
    // API 엔드포인트
    const apiUrl = 'https://api.example.com/hospitals'; // 실제 API URL로 변경하세요

    // 결과를 표시할 컨테이너
    const resultsContainer = document.getElementById('results-container');

    // API 호출 함수
    const fetchData = async () => {
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            displayResults(data);
        } catch (error) {
            console.error('Error fetching data:', error);
            resultsContainer.innerHTML = '<p>데이터를 가져오는 중 오류가 발생했습니다.</p>';
        }
    };

    // 결과를 표시하는 함수
    const displayResults = (hospitals) => {
        resultsContainer.innerHTML = ''; // 기존 내용 초기화
        hospitals.forEach(hospital => {
            const hospitalElement = document.createElement('div');
            hospitalElement.className = 'result-box';
            hospitalElement.innerHTML = `
                <p><strong>병원 이름 :</strong> ${hospital.bplcnm}</p>
                <div class="allergy-box">
                    <p><strong>도로명주소 :</strong> ${hospital.rdnwhladdr}</p>
                </div>
                <div class="allergy-box">
                    <p><strong>업태구분명 :</strong> ${hospital.uptaenm}</p>
                </div>
                <div class="allergy-box">
                    <p><strong>전화번호 :</strong> ${hospital.sitetel}</p>
                </div>
                <div class="allergy-box">
                    <p><strong>영업상태명 :</strong> ${hospital.trdstatenm}</p>
                </div>
            `;
            resultsContainer.appendChild(hospitalElement);
        });
    };

    // 데이터 가져오기
    fetchData();
});
