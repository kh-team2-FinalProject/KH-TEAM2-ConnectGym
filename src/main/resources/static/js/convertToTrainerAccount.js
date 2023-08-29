void (function (D) {
    "use strict";

    /**
     * @type {HTMLCollectionOf.<HTMLFormElement> & {
     *   convertForm: HTMLFormElement
     * }}
     */
    const { convertForm } = D.forms;
    /**
     * @type {HTMLFormControlsCollection & {
     *   profilePhoto: HTMLInputElement
     * }}
     */
     // 프로필 이미지 등록 시
    const { profileImgFile } = convertForm.elements;
    /** @type {HTMLImageElement} */
    const profilePhotoImgEl = convertForm.querySelector(
        "img.convertToTrainerAccount_photo"
    );

    profileImgFile.addEventListener("change", function (e) {
        const file = this.files[0];
        const profilePhotoUrl = URL.createObjectURL(file);
        profilePhotoImgEl.src = profilePhotoUrl;

        setTimeout(() => URL.revokeObjectURL(profilePhotoUrl), 1000);
    });

    // 자격증 이미지 등록 시
        const { licenseImgFiles } = convertForm.elements;
        const licensePhotoDiv = document.getElementById("licensePhotoDiv");
        /** @type {HTMLImageElement} */
        const licensePhotoImgEl = convertForm.querySelector(
            "img.convertToTrainerAccount_photo"
        );

        licenseImgFiles.addEventListener("change", function (e) {

             const files = this.files;
                 for (const file of files) {
              const licensePhotoUrl = URL.createObjectURL(file);

              const imgElement = document.createElement("img");
              imgElement.src = licensePhotoUrl;
              imgElement.classList.add("convertToTrainerAccount_License_photo"); // 이미지 클래스 추가
              imgElement.width = 100;
              licensePhotoDiv.appendChild(imgElement);

              setTimeout(() => URL.revokeObjectURL(licensePhotoUrl), 500);
        }
           });

})(document);
