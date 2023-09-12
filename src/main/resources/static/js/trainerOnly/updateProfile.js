void (function (D) {
    "use strict";

    /**
     * @type {HTMLCollectionOf.<HTMLFormElement> & {
     *   profile_update_form: HTMLFormElement
     * }}
     */
    const {profile_update_form} = D.forms;
    /**
     * @type {HTMLFormControlsCollection & {
     *   profilePhoto: HTMLInputElement
     * }}
     */
        // 프로필 이미지 등록 시
    const {profileImgFile} = profile_update_form.elements;
    /** @type {HTMLImageElement} */
    const profilePhotoImgEl = profile_update_form.querySelector(
        "img.convertToTrainerAccount_photo"
    );

    profileImgFile.addEventListener("change", function (e) {
        const file = this.files[0];
        const profilePhotoUrl = URL.createObjectURL(file);
        profilePhotoImgEl.src = profilePhotoUrl;

        setTimeout(() => URL.revokeObjectURL(profilePhotoUrl), 1000);
    });




})(document);
