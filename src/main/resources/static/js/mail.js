var mails = []

function addMail(id)
{
	if(!mails.find(x=>x==id))
	{
		mails.push(id);
	updateMails();
	getContacts(0);
	document.getElementById("SearchMailInput").value="";
    
	
	}
	console.log(mails)
}
function updateMails()
{
	var mailArea =document.getElementById("selectedMails");
    let mailsFields = "";         
	mails.forEach(
		d=>{
			mailsFields+= 
			          "<div class=\" col-4 col-lg-2\">"
								+"<div class=\" alert alert-success  alert-dismissible fade show\""
								+"role=\"alert\">"
								+"<strong style=\"font-size: x-small;\">Shaikh Shahzade</strong>"
								+"<button type=\"button\" onClick=\"removeMail()\" class=\"btn-close\""
								+ "data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>"
								+"</div>"
								+"</div>"
				
		}
	)
	
	mailArea.innerHTML=mailsFields;
}
function getContacts(value) {
    if(value.length>1)
    {
        fetch("/dashboard/mail/search/id?key="+value)
        .then((res)=>{
          res.json().then((data)=>{
            
              var mailRes =document.getElementById("showMailsResults");
              mailRes.style.display="block";
              htmVal="";
              mailRes.innerHTML=htmVal;
              data.forEach(d =>
                {
                if(!mails.find(x=>x==d.cId))
                {
                htmVal+=
                "<div>"+
                "<button onCLick=\"addMail(id)\" id=\""+d.cId+"\" style=\"width:100%;border-radius:0px;\" type=\"button\" class=\"btn  btn-light\">"
                +"<strong  >"+d.name+ "</strong>"
                +"<small class=\"fw-lighter\" style=\"font-size:60%;\">"+ " ["+d.email+"]" +"</small>"
                +"</button>"+
                "</div>"
                
                mailRes.innerHTML=htmVal;
                }
              });
		
          });
        })
    }
    else
    {
      var mailRes =document.getElementById("showMailsResults");
              mailRes.style.display="none";
    }
  }
  
  function removeMail()
  {
      console.log("calles")
  }