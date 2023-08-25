void (function (D) {
    "use strict";

    /**
     * 결제 폼을 가져온다.
     *
     * @type {HTMLCollectionOf<HTMLFormElement> & {
     *   paymentForm: HTMLFormElement
     * }}
     */
    const { paymentForm } = D.forms;
    /**
     * 결제 폼에서 필드를 가져온다.
     *
     * @type {HTMLFormControlsCollection & {
     *   orderMethod: HTMLInputElement
     *   agreeAll: HTMLInputElement
     *   agree1: HTMLInputElement
     *   agree2: HTMLInputElement
     *   pgShopId: HTMLInputElement
     *   franchiseId: HTMLInputElement
     *   orderNo: HTMLInputElement
     *   userName: HTMLInputElement
     *   userTelNo: HTMLInputElement
     *   price: HTMLInputElement
     * }}
     */
    const {
        agree1,
        agree2,
        agreeAll,
        orderMethod,
        pgShopId,
        franchiseId,
        orderNo,
        userName,
        userTelNo,
        price,
    } = paymentForm.elements;

    /**
     * 동의 버튼 클릭 시 실행되는 이벤트 함수
     *
     * @this {HTMLInputElement}
     * @param {Event} e
     */
    const agreementButtonClickEvent = (e) => {
        if (e.target === agreeAll) {
            agree1.checked = agree2.checked = agreeAll.checked;
        } else if (e.target === agree1 || e.target === agree2) {
            agreeAll.checked = agree1.checked && agree2.checked;
        }
    };

    // 결제 버튼을 눌렀을 시 실행되는 함수
    paymentForm.addEventListener("submit", function (e) {
        e.preventDefault();

        // 동의 항목에 모두 체크하지 않았으면 알림을 띄우고 리턴
        if (!agree1.checked || !agree2.checked || !agreeAll.checked) {
            alert("모든 항목에 동의해 주시기 바랍니다.");
            return;
        }

        // 결제 수단 값을 꺼내와서 올바른 결제 수단이면 계속 진행한다.
        switch (orderMethod.value) {
            case "card":
                break;
            case "phone":
                break;
            default:
                // 결제 수단이 올바르지 않으면 더 이상 진행하지 않고 리턴한다.
                alert("결제 수단을 선택해 주시기 바랍니다.");
                return;
        }

        // 포트원 초기화 함수를 호출한다.
        IMP.init(franchiseId.value);

        // 포트원 결제 요청 함수를 불러 결제를 처리한다.
        IMP.request_pay(
            {
                pg: pgShopId.value,
                pay_method: orderMethod.value,
                merchant_uid: orderNo.value,
                name: "강의",
                amount: +price.value,
                buyer_name: userName.value,
                buyer_tel: userTelNo.value,
                // 모바일에서 결제 시 다음 URL로 리다이렉트
                m_redirect_url: location.origin + "/order/process",
            },
            /**
             * PC에서 결제했을 시 실행되는 함수이다.
             * @param {object} res 포트원 서버에서 받아오는 결과값
             */
            function (res) {
                // PC에서 결제 시 콜백 실행
                console.log("결과", res);

                if (res.success) {
                    // 서버로 보낼 전송값을 넣는다.
                    const body = new URLSearchParams({
                        imp_uid: res.imp_uid,
                        merchant_uid: res.merchant_uid,
                    });

                    // 포트원 서버에서 오류 코드나 메시지를 반환했을 때만 오류 내용을 설정한다.
                    res.error_msg && body.set("error_msg", res.error_msg);
                    res.error_code && body.set("error_code", res.error_code);

                    // 서버로 요청을 전송해서 결제를 처리한다.
                    fetch("/api/order/process", {
                        method: "POST",
                        body,
                    })
                        .then((res) => res.json())
                        .then((v) => {
                            if (v.success) {
                                // 성공하면 서버에서 반환한 URL로 이동한다.
                                location.href = v.url;
                            }
                        });
                } else {
                    alert(res.error_msg);
                }
            }
        );
    });

    // 동의 버튼을 눌렀을 때 이벤트가 등록되도록 한다.
    agree1.addEventListener("change", agreementButtonClickEvent);
    agree2.addEventListener("change", agreementButtonClickEvent);
    agreeAll.addEventListener("change", agreementButtonClickEvent);
})(document);
