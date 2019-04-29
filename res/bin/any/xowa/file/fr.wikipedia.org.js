/* jshint scripturl:true, laxbreak:true, loopfunc:true */
/* global mw, $, importScript */
/**
 * N'importe quel JavaScript ici sera charg� pour n'importe quel utilisateur et pour chaque page acc�d�e.
 *
 * ATTENTION : Avant de modifier cette page, veuillez tester vos changements avec votre propre
 * vector.js. Une erreur sur cette page peut faire bugger le site entier (et g�ner l'ensemble des
 * visiteurs), m�me plusieurs heures apr�s la modification !
 *
 * Pri�re de ranger les nouvelles fonctions dans les sections adapt�es :
 * - Fonctions JavaScript
 * - Fonctions sp�cifiques pour MediaWiki
 * - Applications sp�cifiques � la fen�tre d'�dition
 * - Applications qui peuvent �tre utilis�es sur toute page
 * - Applications sp�cifiques � un espace de nom ou une page
 *
 * <nowiki> /!\ Ne pas retirer cette balise
 */


/**********************************************************************************************************/
/* Fonctions g�n�rales MediaWiki (pallient les limitations du logiciel)                                   */
/* Surveiller : https://git.wikimedia.org/history/mediawiki%2Fcore.git/HEAD/skins%2Fcommon%2Fwikibits.js  */
/**********************************************************************************************************/

/**
 * Projet JavaScript
 */
window.obtenir = function ( name ) {
	if ( mw.loader.getState( 'ext.gadget.' + name ) !== null ) {
		mw.loader.load( 'ext.gadget.' + name );
	} else {
		importScript( 'MediaWiki:Gadget-' + name + '.js' );
	}
};

/**
 * Transformer les pages du Bistro, du BA et les pages sp�cifi�es en page de discussion
 */
if ( mw.config.get( 'wgNamespaceNumber' ) >= 2 ) {
	$( function ( $ ) {
		if (
			/^Wikip�dia:(Le_Bistro|Bulletin_des_administrateurs|Questions_techniques)/.test( mw.config.get( 'wgPageName' ) ) ||
			$( '#transformeEnPageDeDiscussion' ).length
		) {
			$( 'body' ).removeClass( 'ns-subject' ).addClass( 'ns-talk' );
		}
	} );
}


/****************************************/
/* Applications pour l'ensemble du site */
/****************************************/

/**
 * Tout ce qui concerne la page d'�dition
 */
if ( [ 'edit', 'submit' ].indexOf( mw.config.get( 'wgAction' ) ) !== -1 ) {

	// chargement de [[MediaWiki:Gadget-CommonEdit.js]]
	mw.loader.load( 'ext.gadget.CommonEdit' );

	// pour que les fonctions soient d�finies d�s maintenant,
	// mais l'ex�cution r�elle ne se fait qu'une fois le module charg�
	window.addSpecialCharset = function ( title, chars ) {
		mw.loader.using( 'ext.gadget.CommonEdit', function () {
			window.realAddSpecialCharset( title, chars );
		} );
	};
	window.addSpecialCharsetHTML = function ( title, charsHTML ) {
		mw.loader.using( 'ext.gadget.CommonEdit', function () {
			window.realAddSpecialCharsetHTML( title, charsHTML );
		} );
	};

	// fonction pour ajouter un bouton � la fin de la barre d'outils
	// permet d'utiliser [[MediaWiki:Gadget-MonobookToolbar.js]] sans se pr�occuper de son chargement
	window.addCustomButton = ( function () {
		var promise;

		return function () {
			var buttonArguments = [].slice.call( arguments );

			if ( !promise ) {
				promise = mw.loader.using( 'ext.gadget.MonobookToolbar' );
			}

			promise.done( function () {
				MonobookToolbar.addButton.apply( MonobookToolbar, buttonArguments );
			} );
		};
	} )();

} else {
	// pour que les fonctions soient toujours d�finies,
	// afin d'�viter aux scripts utilisateur de planter
	window.addSpecialCharset = function () {};
	window.addSpecialCharsetHTML = function () {};
	window.addCustomButton = function () {};
}

/**
 * R��criture des titres
 *
 * Fonction utilis�e par [[Mod�le:Titre incorrect]]
 *
 * La fonction cherche un bandeau de la forme
 * <div id="RealTitleBanner">
 *   <span id="RealTitle">titre</span>
 * </div>
 *
 * Un �l�ment comportant id="DisableRealTitle" d�sactive la fonction
 */
function rewritePageTitle( $ ) {
	var $realTitle, titleHtml, $h1,
		$realTitleBanner = $( '#RealTitleBanner' );
	if ( $realTitleBanner.length && !$( '#DisableRealTitle' ).length ) {
		$realTitle = $( '#RealTitle' );
		$h1 = $( 'h1:first' );
		if ( $realTitle.length && $h1.length ) {
			titleHtml = $realTitle.html();
			if ( titleHtml === '' ) {
				$h1.hide();
			} else {
				$h1.html( titleHtml );
				if ( mw.config.get( 'wgAction' ) === 'view' ) {
					// using a callback for replacement, to prevent interpreting "$" characters that realTitle might contain
					document.title = document.title.replace( /^.+( [��-] Wikip�dia)$/, function ( match, p1 ) {
						return $realTitle.text() + p1;
					} );
				}
			}
			$realTitleBanner.hide();
			$( '<p>' ).css( 'font-size', '80%' )
				.append( 'Titre � utiliser pour cr�er un lien interne : ', $( '<b>' ).text( mw.config.get( 'wgPageName' ).replace( /_/g, ' ' ) ) )
				.insertAfter( $h1 );
		}
	}
}
$( rewritePageTitle );


/**
 * Ajout d'un sous-titre
 *
 * Fonction utilis�e par [[Mod�le:Sous-titre]]
 *
 * La fonction cherche un �l�ment de la forme
 * <span id="sous_titre_h1">Sous-titre</span>
 */

function sousTitreH1( $content ) {
	$( '#firstHeading > #sous_titre_h1' ).remove();
	var $span = $content.find( '#sous_titre_h1' );
	if ( $span.length ) {
		$span.prepend( ' ' );
		$( '#firstHeading' ).append( $span );
	}
}
mw.hook( 'wikipage.content' ).add( sousTitreH1 );


/**
 * Bo�tes d�roulantes
 *
 * Pour [[Mod�le:M�ta palette de navigation]]
 */

var Palette_Derouler = '[afficher]';
var Palette_Enrouler = '[masquer]';

var Palette_max = 1;

function Palette_toggle( $table ) {
	$table.find( 'tr:not(:first)' ).toggleClass( 'navboxHidden' );
}

function Palette( $content ) {

	var $tables = $content.find( 'table.collapsible' );
	var groups = {};

	$tables.each( function( _, table ) {
		var group = table.dataset.autocollapseGroup || '__default__';
		groups[group] = ( groups[group] || 0 ) + 1;
	} );

	$tables.each( function( _, table ) {
		var $table = $( table );

		var group = table.dataset.autocollapseGroup || '__default__';
		var autoCollapse = groups[group] > Palette_max;
		var collapsed = $table.hasClass( 'collapsed' ) || ( autoCollapse && $table.hasClass( 'autocollapse' ) );

		// le mod�le dispose d'une classe "navbox-title",
		// sauf que les palettes "inlin�es" (e.g. � {| class="navbox collapsible collapsed" �) n'ont pas cette classe
		$table.find( 'tr:first th:first' ).prepend(
			$( '<span class="navboxToggle">\u00a0</span>' ).append(
				$( '<a href="javascript:">' + (collapsed ? Palette_Derouler : Palette_Enrouler) + '</a>' ).click( function ( e ) {
					e.preventDefault();
					if ( this.textContent === Palette_Enrouler ) {
						this.textContent = Palette_Derouler;
					} else {
						this.textContent = Palette_Enrouler;
					}
					Palette_toggle( $table );
				} )
			)
		);
		if ( collapsed ) {
			Palette_toggle( $table );
		}
	} );

	// permet de d�rouler/enrouler les palettes en cliquant n'importe o� sur l'ent�te
	// (utilisation de la classe "navbox-title", comme �a seules les vraies palettes utilisant le mod�le sont cibl�es)
	$content.find( '.navbox-title' )
		.click( function ( e ) {
			if ( $( e.target ).closest( 'a' ).length ) {
				return;
			}
			$( this ).find( '.navboxToggle a' ).click();
		} )
		.css( 'cursor', 'pointer' );
}
mw.hook( 'wikipage.content' ).add( Palette );


/**
 * Pour [[Mod�le:Bo�te d�roulante]]
 */

var BoiteDeroulante_Derouler = '[afficher]';
var BoiteDeroulante_Enrouler = '[masquer]';

function BoiteDeroulante_toggle(NavToggle){
	var NavFrame = NavToggle.parentNode;

	var caption = [];
	caption[0] = NavFrame.dataset.boiteDeroulanteDerouler;
	caption[1] = NavFrame.dataset.boiteDeroulanteEnrouler;

	var $NavContent = $(NavFrame).find('.NavContent').first();

	if ( NavToggle.textContent === caption[1] ) {
		NavToggle.textContent = caption[0];
		$NavContent.hide();
	} else {
		NavToggle.textContent = caption[1];
		$NavContent.show();
	}
}

function BoiteDeroulante( $content ) {

	$content.find( '.NavFrame' ).each( function ( _, NavFrame ) {
		var CustomTexts, Derouler, Enrouler, NavToggle;

		if (NavFrame.title && NavFrame.title.indexOf("/") !== -1) {
			CustomTexts = NavFrame.title.split("/");
			Derouler = CustomTexts[0];
			Enrouler = CustomTexts[1];
		} else {
			Derouler = BoiteDeroulante_Derouler;
			Enrouler = BoiteDeroulante_Enrouler;
		}
		NavFrame.title = '';
		NavFrame.dataset.boiteDeroulanteDerouler = Derouler;
		NavFrame.dataset.boiteDeroulanteEnrouler = Enrouler;

		NavToggle = document.createElement("a");
		NavToggle.className = 'NavToggle';
		NavToggle.href = 'javascript:';
		NavToggle.onclick = function (e) {
			e.preventDefault();
			BoiteDeroulante_toggle(e.target);
		};
		NavToggle.textContent = Enrouler;

		NavFrame.insertBefore(NavToggle, NavFrame.firstChild);

		BoiteDeroulante_toggle(NavToggle);
	} );

	// permet de d�rouler/enrouler les bo�tes en cliquant n'importe o� sur l'ent�te
	$content.find( '.NavHead' )
		.click( function ( e ) {
			if ( $( e.target ).closest( 'a' ).length ) {
				return;
			}
			var toggle = $( this ).siblings( 'a.NavToggle' )[0];
			if ( toggle ) {
				toggle.click(); // pas du jquery, mais du vanilla js
			}
		} )
		.css( 'cursor', 'pointer' );
}

mw.hook( 'wikipage.content' ).add( BoiteDeroulante );


/**
 * Fonctionnement du [[Mod�le:Animation]]
 * Le JavaScript principal se situe dans [[MediaWiki:Gadget-Diaporama.js]]
 */
mw.hook( 'wikipage.content' ).add( function ( $content ) {
	if ( $content.find( '.diaporama' ).length ) {
		mw.loader.using( 'ext.gadget.Diaporama', function () {
			Diaporama_Init( $content );
		} );
	}
} );


/**
 * Permet d'afficher les cat�gories cach�es pour les contributeurs enregistr�s, en ajoutant un (+) � la mani�re des bo�tes d�roulantes
 */
function hiddencat( $ ) {
	//if (mw.util.getParamValue('printable') === 'yes') {
	//	return;
	//}
	var cl = document.getElementById('catlinks');
	if (!cl) {
		return;
	}
	var $hc = $('#mw-hidden-catlinks');
	if ( !$hc.length ) {
		return;
	}
	if ( $hc.hasClass('mw-hidden-cats-user-shown') ) {
		return;
	}
	if ( $hc.hasClass('mw-hidden-cats-ns-shown') ) {
		$hc.addClass('mw-hidden-cats-hidden');
	}
	var nc = document.getElementById('mw-normal-catlinks');
	if ( !nc ) {
		var catline = document.createElement('div');
		catline.id = 'mw-normal-catlinks';
		var a = document.createElement('a');
		a.href = '/wiki/Cat�gorie:Accueil';
		a.title = 'Cat�gorie:Accueil';
		a.appendChild(document.createTextNode('Cat�gories'));
		catline.appendChild(a);
		catline.appendChild(document.createTextNode(' : '));
		nc = cl.insertBefore(catline, cl.firstChild);
	}
	var lnk = document.createElement('a');
	lnk.id = 'mw-hidden-cats-link';
	lnk.title = 'Cet article contient des cat�gories cach�es';
	lnk.style.cursor = 'pointer';
	lnk.style.color = 'black';
	lnk.style.marginLeft = '0.3em';
	$(lnk).click(toggleHiddenCats);
	lnk.appendChild(document.createTextNode('[+]'));
	nc.appendChild(lnk);
}

function toggleHiddenCats(e) {
	var $hc = $('#mw-hidden-catlinks');
	if ( $hc.hasClass('mw-hidden-cats-hidden') ) {
		$hc.removeClass('mw-hidden-cats-hidden');
		$hc.addClass('mw-hidden-cat-user-shown');
		$(e.target).text('[�]');
	} else {
		$hc.removeClass('mw-hidden-cat-user-shown');
		$hc.addClass('mw-hidden-cats-hidden');
		$(e.target).text('[+]');
	}
}

xowa.js.load_lib(xowa.root_dir + 'bin/any/xowa/html/res/src/mediawiki/mediawiki.util.js', function () {
//mw.loader.using('mediawiki.util', function () {
	$( hiddencat );
});


/**
 * Script pour alterner entre plusieurs cartes de g�olocalisation
 */

function GeoBox_Init($content){

	$content.find( 'div.img_toogle' ).each( function ( i, Container ) {
		Container.id = 'img_toogle_' + i;
		var Boxes = $( Container ).find( '.geobox' );
		var ToggleLinksDiv = document.createElement('ul');
		ToggleLinksDiv.id = 'geoboxToggleLinks_' + i;
		Boxes.each( function ( a, ThisBox ) {
			ThisBox.id = 'geobox_' + i + "_" + a;
			ThisBox.style.borderTop='0';
			var ThisAlt = ThisBox.getElementsByTagName('img')[0].alt;
			var toggle = document.createElement('a');
			toggle.id = 'geoboxToggle_' + i + "_" + a;
			toggle.appendChild(document.createTextNode(ThisAlt));
			toggle.href = 'javascript:';
			toggle.onclick = function (e) {
				e.preventDefault();
				GeoBox_Toggle(this);
			};
			var Li = document.createElement('li');
			Li.appendChild(toggle);
			ToggleLinksDiv.appendChild(Li);
			if (a === (Boxes.length - 1)) {
				Li.style.display = "none";
			} else {
				ThisBox.style.display = "none";
			}
		} );
		Container.appendChild(ToggleLinksDiv);
	} );
}

function GeoBox_Toggle(link){
	var ImgToggleIndex = link.id.replace('geoboxToggle_', '').replace(/_.*/g, "");
	var GeoBoxIndex = link.id.replace(/.*_/g, "");
	var ImageToggle = document.getElementById('img_toogle_' + ImgToggleIndex);
	var Links = document.getElementById('geoboxToggleLinks_' + ImgToggleIndex);
	var Geobox = document.getElementById('geobox_' + ImgToggleIndex + "_" + GeoBoxIndex);
	var Link = document.getElementById('geoboxToggle_' + ImgToggleIndex + "_" + GeoBoxIndex);
	if ( (!ImageToggle) || (!Links) || (!Geobox) || (!Link) ) {
		return;
	}
	$( ImageToggle ).find( '.geobox' ).each( function ( _, ThisgeoBox ) {
		if (ThisgeoBox.id === Geobox.id) {
			ThisgeoBox.style.display = "";
		} else {
			ThisgeoBox.style.display = "none";
		}
	} );
	$( Links ).find( 'a' ).each( function ( _, thisToggleLink ) {
		if (thisToggleLink.id === Link.id){
			thisToggleLink.parentNode.style.display = "none";
		} else {
			thisToggleLink.parentNode.style.display = "";
		}
	} );
}

mw.hook( 'wikipage.content' ).add( GeoBox_Init );


/**
 * permet d'ajouter un petit lien (par exemple d'aide) � la fin du titre d'une page.
 * utilis� par [[Mod�le:Aide contextuelle]]
 * known bug : conflit avec le changement de titre classique.
 * Pour les commentaires, merci de contacter [[user:Plyd|Plyd]].
 */
function rewritePageH1bis() {
	var helpPage = document.getElementById("helpPage");
	if (helpPage) {
		var h1 = document.getElementById('firstHeading');
		if (h1) {
			h1.innerHTML += '<span id="h1-helpPage">' + helpPage.innerHTML + '</span>';
		}
	}
}
$( rewritePageH1bis );

/**
 * Configuration du tri des diacritique dans les tables de class "sortable"
 */
//mw.config.set( 'tableSorterCollation', {'�':'a', '�':'a', '�':'ae', '�':'e', '�':'e', '�':'e', '�':'i', '�':'i', '�':'o', '�':'oe', '�':'u', '�':'c',  } );

/**
 * Direct imagelinks to Commons
 *
 * Required modules: mediawiki.RegExp, mediawiki.util, user.options
 *
 * @source www.mediawiki.org/wiki/Snippets/Direct_imagelinks_to_Commons
 * @author Krinkle
 * @version 2015-06-23
 * Ajout� le 'uselang' ce 18 janvier 2016 � Ltrlg
 */
//if ( mw.config.get( 'wgNamespaceNumber' ) >= 0 ) {
//	mw.loader.using( [ 'mediawiki.RegExp', 'mediawiki.util', 'user.options' ] ).done(function(){
//		mw.hook( 'wikipage.content' ).add( function ( $content ) {
//			var
//				uploadBase = '//upload.wikimedia.org/wikipedia/commons/',
//
//				fileNamespace = mw.config.get( 'wgFormattedNamespaces' )['6'],
//				localBasePath = new RegExp( '^' + mw.RegExp.escape( mw.util.getUrl( fileNamespace + ':' ) ) ),
//				localBaseScript = new RegExp( '^' + mw.RegExp.escape( mw.util.wikiScript() + '?title=' + mw.util.wikiUrlencode( fileNamespace + ':' ) ) ),
//
//				commonsBasePath = '//commons.wikimedia.org/wiki/File:',
//				commonsBaseScript = '//commons.wikimedia.org/w/index.php?title=File:',
//
//				lang = mw.user.options.get( 'language' );
//
//			$content.find( 'a.image' ).attr( 'href', function ( i, currVal ) {
//				if ( $( this ).find( 'img' ).attr( 'src' ).indexOf( uploadBase ) === 0 ) {
//					if ( localBasePath.test( currVal ) ) {
//						return currVal.replace( localBasePath, commonsBasePath ) + '?uselang=' + lang;
//					} else if ( localBaseScript.test( currVal ) ) {
//						return currVal.replace( localBaseScript, commonsBaseScript ) + '&uselang=' + lang;
//					} else {
//						return currVal;
//					}
//				}
//			} );
//		} );
//	} );
//}

/**
 * Ajout d'un lien � ajouter une section � en bas de page
 */
if ( mw.config.get( 'wgAction' ) === 'view' ) {
	$( function( $ ) {
		var $newSectionLink = $( '#ca-addsection' ).find( 'a' );
		if ( $newSectionLink.length ) {
			$( '#mw-content-text' ).append(
				'<div style="text-align:right; font-size:0.9em; margin:1em 0 -0.5em">'
				+ '<a href="' + $newSectionLink.attr( 'href' ) + '" title="Commencer une nouvelle section">Ajouter un sujet</a>'
				+ '</div>'
			);
		}
	} );
}

/**
 * Repositionnement de la page sur l'ancre avec laquelle elle a �t� appel�e
 * apr�s le repli des bo�tes d�roulantes, entre autres.
 */
if ( window.location.hash ) {
	$( function ( $ ) {
		setTimeout( function () {
			var currentTarget = document.getElementById( decodeURIComponent( window.location.hash.substring( 1 ) ) );
			if ( currentTarget ) {
				currentTarget.scrollIntoView();
			}
		}, 1 );
	} );
}


/************************************************************/
/* Function Strictement sp�cifiques � un espace de nom ou � une page */
/************************************************************/

// ESPACE DE NOM 'SPECIAL'
if ( mw.config.get( 'wgNamespaceNumber' ) === -1 ) {

/**
 * Ajoute le namespace aux filtres personnalis�s sur [[Sp�cial:Pages li�es]]
 * Voir aussi [[MediaWiki:Linkshere]]
 */
if ( mw.config.get( 'wgCanonicalSpecialPageName' ) === 'Whatlinkshere' ) {

	mw.loader.using( 'mediawiki.Uri', function () {
		$( function ( $ ) {

			var query = ( new mw.Uri( null, { overrideKeys: true } ) ).query;

			var append = ( query.namespace ? '&namespace=' + encodeURIComponent( query.namespace ) : '' )
				+ ( query.invert ? '&invert=' + encodeURIComponent( query.invert ) : '' );

			if ( append !== '' ) {
				$( '#whatlinkshere-customfilters' ).find( 'a' ).each( function () {
					this.href += append;
				} );
			}
		} );
	} );
}

/**
 * Affiche un mod�le Information sur la page de t�l�chargement de fichiers [[Sp�cial:T�l�chargement]]
 * Voir aussi [[MediaWiki:Onlyifuploading.js]]
 */
if ( mw.config.get( 'wgCanonicalSpecialPageName' ) === 'Upload' ) {
	importScript( 'MediaWiki:Onlyifuploading.js' );
}

/**
 * Supprime de la liste des balises disponibles et de la liste des balises supprimables
 * certaines balises r�serv�es � des outils automatiques
 */
if ( mw.config.get( 'wgCanonicalSpecialPageName' ) === 'EditTags' ) {
	importScript( 'MediaWiki:Common.js/EditTags.js' );
}

} // Fin du code concernant l'espace de nom 'Special'


// ESPACE DE NOM 'UTILISATEUR'
if ( mw.config.get( 'wgNamespaceNumber' ) === 2 ) {

/*
 * Fonctionnement du [[Mod�le:Cadre � onglets]]
 * Le JavaScript principal se situe dans [[MediaWiki:Gadget-CadreOnglets.js]]
 */
mw.hook( 'wikipage.content' ).add( function ( $content ) {
	if ( $content.find( '.cadre_a_onglets' ).length ) {
		mw.loader.using( 'ext.gadget.CadreOnglets', function () {
			CadreOnglets_Init( $content );
		} );
	}
} );

} // Fin du code concernant l'espace de nom 'Utilisateur'


// ESPACE DE NOM 'R�F�RENCE'
if ( mw.config.get( 'wgNamespaceNumber' ) === 104 ) {

/*
 * Choix du mode d'affichage des r�f�rences
 * @note L'ordre de cette liste doit correspondre a celui de Mod�le:�dition !
 */

var addBibSubsetMenu = function ( $content ) {
	var $specialBib = $content.find( '#specialBib' );
	if ( !$specialBib.length ) {
		return;
	}

	// select subsection of special characters
	var chooseBibSubset = function ( s ) {
		$content.find( '.edition-Liste' ).css( 'display', s === 0 ? 'block' : 'none' );
		$content.find( '.edition-WikiNorme' ).css( 'display', s === 1 ? 'block' : 'none' );
		$content.find( '.edition-BibTeX' ).css( 'display', s === 2 ? 'block' : 'none' );
		$content.find( '.edition-ISBD' ).css( 'display', s === 3 ? 'block' : 'none' );
		$content.find( '.edition-ISO690' ).css( 'display', s === 4 ? 'block' : 'none' );
	};

	var $menu = $( '<select>' )
		.css( 'display', 'inline' )
		.change( function () {
			chooseBibSubset( this.selectedIndex );
		} )
		.append(
			$( '<option>' ).text( 'Liste' ),
			$( '<option>' ).text( 'WikiNorme' ),
			$( '<option>' ).text( 'BibTeX' ),
			$( '<option>' ).text( 'ISBD' ),
			$( '<option>' ).text( 'ISO690' )
		);

	$specialBib.append( $menu );

	/* default subset - try to use a cookie some day */
	chooseBibSubset( 0 );
};

mw.hook( 'wikipage.content' ).add( addBibSubsetMenu );

} // Fin du code concernant l'espace de nom 'R�f�rence'


// PAGES SP�CIFIQUES

// Personnalisation des liens dans les pages d'aide selon un param�tre de l'URL.
// Utilis� par [[Aide:Comment cr�er un article/publier]].
function ReplaceSourcePageInLinks() {
	var match = window.location.search.match( /[?&]sourcepage=([^&]*)/ );
	if ( !match ) {
		return;
	}
	var page = decodeURIComponent( match[1] );
	$( '.sourcepage-subst a' ).each( function() {
		if ( /^(https?:)?\/\/[^/]+\.wikipedia\.org\//.test( this.href ) ) {
			this.href = this.href.replace( 'TITRE-A-REMPLACER', encodeURIComponent( page ) );
		}
	} );
}

if ( mw.config.get( 'wgPageName' ) === 'Aide:Comment_cr�er_un_article/publier' ) {
	$( ReplaceSourcePageInLinks );
}

/* EXTRAS */

/* fr.wikipedia.org/wiki/MediaWiki:Gadget-ArchiveLinks.js */
/**
 * Application de [[Wikip�dia:Prise de d�cision/Syst�me de cache]].
 * Un <span class="noarchive"> autour d'un lien l'emp�che d'�tre pris en compte.
 *
 * {{Cat�gorisation JS|ArchiveLinks}}
 */

if ( !window.no_external_cache && ( mw.config.get( 'wgNamespaceNumber' ) === 0 || mw.user.options.get( 'gadget-ExtendedCache' ) ) ) {
	mw.hook( 'wikipage.content' ).add( function ( $content ) {

		$content.find( '.external' ).each( function ( _, link ) {
			if ( link.tagName !== 'A' ) {
				return;
			}

			var chemin = link.href;

			if ( /(^|\.)wiki([pm]edia|data)\.org$/.test( link.hostname )
				|| chemin.indexOf( 'http://tools.wmflabs.org/' ) === 0
				|| chemin.indexOf( 'http://archive.wikiwix.com/cache/' ) === 0
				|| chemin.indexOf( 'http://wikiwix.com/cache/' ) === 0
				|| chemin.indexOf( 'http://web.archive.org/web/' ) === 0
			) {
				return;
			}

			var $link = $( link );

			if ( $link.closest( '.noarchive, .infobox_v3' ).length ) {
				return;
			}

			// s�curit� : attention � �chapper les quotes dans les attributs

			var href = 'http://archive.wikiwix.com/cache/?url=' + encodeURIComponent( chemin );
			var title = 'archive sur Wikiwix';

			var archiveLink = '<a href="' + href + '" title="' + title + '">archive</a>';

			$link.after( '<small class="cachelinks">\u00a0[' + archiveLink + ']</small>' );
		});
	});
}
