var mails = []
var map = new Map();
function addMail(id) {
	if (!mails.find(x => x == id)) {
		mails.push(id);
		updateMails();
		getContacts(0);
		document.getElementById("SearchMailInput").value = "";


	}
	console.log(mails)
}
function updateMails() {
	var mailArea = document.getElementById("selectedMails");
	let mailsFields = "";
	console.log(map)
	mails.forEach(
		d => {
			currentMail = map.get(d);
			console.log(currentMail)
			if (currentMail !== null) {
				mailsFields +=
					"<div class=\"col-4 mx-1 col-lg-2 alert alert-success  alert-dismissible fade show\""
					+ "role=\"alert\">"
					+ "<strong style=\"font-size: x-small;\">"
					+ currentMail.name

					+ "</strong>"
					+ "<button type=\"button\" "
					+ "onClick=\"removeMail(id)\" id=\"" + d + "\" class=\"btn-close\""
					+ "data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>"
					+ "</div>";
			}
		}
	)

	mailArea.innerHTML = mailsFields;

	var mailIdInputHolder = document.getElementById("mailIdInputHolder");
	mailsInputIds = ""
	mails.forEach(
		x=>{
			mailsInputIds+= "<Input  type=\"text\" value=\""+x+"\" name=\"mailIds[]\" >"
		}
	) 
	
	
	mailIdInputHolder.innerHTML = mailsInputIds;


}
function getContacts(value) {
	if (value.length > 1) {
		fetch("/dashboard/mail/search/id?key=" + value)
			.then((res) => {
				res.json().then((data) => {

					var mailRes = document.getElementById("showMailsResults");
					mailRes.style.display = "block";
					htmVal = "";
					mailRes.innerHTML = htmVal;
					data.forEach(d => {
						if (!mails.find(x => x == d.cId)) {
							map.set(d.cId + "", d);
							htmVal +=
								"<div>" +
								"<button onCLick=\"addMail(id)\" id=\"" + d.cId + "\" style=\"width:100%;border-radius:0px;\" type=\"button\" class=\"btn  btn-light\">"
								+ "<strong  >" + d.name + "</strong>"
								+ "<small class=\"fw-lighter\" style=\"font-size:60%;\">" + " [" + d.email + "]" + "</small>"
								+ "</button>" +
								"</div>"

							mailRes.innerHTML = htmVal;
						}
					});

				});
			})
	}
	else {
		var mailRes = document.getElementById("showMailsResults");
		mailRes.style.display = "none";
	}
}

function removeMail(id) {

	mails = mails.filter((x) => x != id)

	console.log(mails)
}