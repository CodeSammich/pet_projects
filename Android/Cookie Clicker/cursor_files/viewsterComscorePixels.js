document.write(unescape("%3Cscript src='" + (document.location.protocol == "https:" ? "https://sb" : "http://b") + ".scorecardresearch.com/beacon.js' %3E%3C/script%3E"));

function trackVideoView(contentType) {
	var c5 = null;
	var contentType = contentType.toLowerCase();
	switch (contentType) {
		case "pre":
			c5 = 09;
			break;
		case "mid":
			c5 = 11;
			break;
		case "post":
			c5 = 10;
			break;
		case "movie":
			c5 = 03;
			break;
		case "main":
			c5 = 02;
			break;
	}
	COMSCORE.beacon({ c1: 1,
		c2: "14380036",
		c3: "",
		c4: "",
		c5: c5,
		c6: "",
		c10: "",
		c14: ""});
}
