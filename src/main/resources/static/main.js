document.addEventListener("DOMContentLoaded", function(event) {
  $('table #deleteButton').on('click',function(event)
  {
  event.preventDefault();
  var href = $(this).attr('href');
  $('#exampleModalCenter #delRef1').attr('href',href);
  $('#exampleModalCenter').modal({show : true});
  }
  );

    $('table #search').on('click',function(event)
    {
    event.preventDefault();
    var href = $(this).attr('href');
    $('#exampleModalCenter #delRef1').attr('href',href);
    $('#exampleModalCenter').modal({show : true});
    }
    );


});