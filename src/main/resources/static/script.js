const baseurl = 'http://localhost:12345/api/cards';

$(document).on('click', 'a[data-card-id]', function (e) {
	e.preventDefault();
	const cardId = $(this).data('cardId');
	
	$.getJSON(baseurl + '/' + cardId, function (data)	{
		data.companyName = data.companyName || '<i>No company specified</i>';
		$('#card-detail')
			.html(`
				<h1>${data.firstName} ${data.lastName}</h1>
				<h2>${data.companyName}</h2>
				<div>Title: ${data.title}</div>
			`);
	});
});

$(searchbutton).on('click', function(e)	{
	e.preventDefault();
	const searchString = $('#searchbox');
	console.log(searchString.val());
	$('#card-list').html(``);
	$.getJSON(baseurl + '?lastName=' + searchString.val(), function (data)	{
		if (data.length)	{
			for (let card of data)	{
				$('<tr></tr>')
				.html(`
					<td>
						<a href="#" data-card-id="${card.id}">
							${card.firstName} ${card.lastName}
						</a>	
					</td>
				`)
				.appendTo($('#card-list'));
				console.log(data);
			}
		} else	{
			$('<tr></tr>')
				.css('color', 'red')
				.html('You have no data')
				.appendTo($('#card-list'));
		}
	});

});

$.getJSON(baseurl, function (data)	{
	if (data.length)	{
		for (let card of data)	{
			$('<tr></tr>')
			.html(`
				<td>
					<a href="#" data-card-id="${card.id}">
						${card.firstName} ${card.lastName}
					</a>	
				</td>
			`)
			.appendTo($('#card-list'));
			console.log(data);
		}
	} else	{
		$('<tr></tr>')
			.css('color', 'red')
			.html('You have no data')
			.appendTo($('#card-list'));
	}
});