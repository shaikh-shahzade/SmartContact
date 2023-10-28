var mails = []

function addMail(id)
{
	mails.push(id)
	console.log(mails)
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
              data.forEach(d =>
                htmVal+=
                "<div>"+
                "<button onCLick=\"addMail(id)\" id=\""+d.cId+"\" style=\"width:100%;border-radius:0px;\" type=\"button\" class=\"btn  btn-light\">"
                +"<strong  >"+d.name+ "</strong>"
                +"<small class=\"fw-lighter\" style=\"font-size:60%;\">"+ " ["+d.email+"]" +"</small>"
                +"</button>"+
                "</div>"
                
                );
						
              mailRes.innerHTML=htmVal;
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