function getContacts(value) {
    if(value.length>1)
    {
        fetch("/dashboard/mail/search/id?key="+value)
        .then((res)=>{
          res.json().then((data)=>{
              $("#showMailsResults")
              console.log(data);
          });
        })
    }
  }
  
  function removeMail()
  {
      console.log("calles")
  }