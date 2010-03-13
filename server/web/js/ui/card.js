// globals
if (!window.runtime) {
    airapp = false;
} else {
    airapp = true;
}

lastRoot = 'api';

$.fn.textDropShadow = function() {
    return this.each(function () {
        var $text = $(this);
        $text.html('<span class="jq-shadow">' + $text.html() + '</span><span>' + $text.html() + '</span>');
        return $text;
    });
};

function apiBrowserMain() {
    $('#navigation a').textDropShadow();
    $('#navigation label').prepend('<span class="jq-shadow">Filter</span>');

    // handle external links
    window.favs = {};

    if (airapp) {
        $('#credit a').click(function () {
            window.runtime.flash.net.navigateToURL(new window.runtime.flash.net.URLRequest(this.href));
            return false;
        });
    }

    $(window).click(function (event) {
        var $elm = $(event.target);
        if (event.target.nodeName == 'SPAN' && $elm.is('.fav')) {
            $elm.toggleClass('on');

            // silly hack for last minute bug
            var key = $elm.attr('id').toLowerCase().replace(/^\$\./, '');

            if ($elm.is('.on')) {
                // bit dopy, and hacky, but will do for now
                jquerydocs.data[key].fav = 'true';
                favs[key] = 1;
            } else {
                jquerydocs.data[key].fav = 'false';
                delete favs[key];
            }

            if (!airapp) {
                // commit fav to cookie
                createCookie('favs', $.toJSON(favs), 365);
            } else {
                var FileSystem = window.runtime.flash.filesystem;

                var file = FileSystem.File.applicationStorageDirectory.resolvePath('favs.json');
                var fileStream = new FileSystem.FileStream();
                fileStream.open(file, FileSystem.FileMode.WRITE);
                fileStream.writeUTFBytes($.toJSON(favs));
                fileStream.close();
            }

            return false;
        }
    });

    var timer = null;
    var last = '';
    $('#query').keyup(function () {
        if (this.value != last) {
            if (timer) {
                clearTimeout(timer);
            }
            last = this.value;
            if (jquerydocs.find) {
                timer = setTimeout(queryDocs, 250);
            }
        }
    }).focus(function () {
        $(this).parent().addClass('selected');
    }).blur(function () {
        $(this).parent().removeClass('selected');
    });

    function queryDocs() {
        if (jquerydocs == null) {
            return;
        }
        jquerydocs = _jquerydocs; // reset

        // the query trimmed
        var q = $('#query').val().toLowerCase().replace(/^\s+|\s+$/g, "");

        if (q == '') {
            loadCategories();
            return;
        }

        var $wrapper = $('#browser > div');
        $wrapper.empty();

        var items = [];

        if (q.indexOf('=') !== -1) {
            q = q.split('=');
            items = jquerydocs.find(q[1], q[0]); // hidden feature to search by a specific field, i.e. added=1.3
        } else {
            items = jquerydocs.find(q);
        }

        showFunctions(items, $wrapper);
        if (items.length) {
            $('#functions').show();
        } else {
            $('#browser > div').html('<p class="loading">No functions found.</p>');
        }
    }

    if (!window.jquerydocs.initialised) {
        loadDocs('lib/docs/api-docs.xml'); // from api-loader.js and triggers api-load-complete
    } else {
        init();
    }
}

$(document).bind('api-load-complete', function () {
    // cache the original
    window._jquerydocs = jquerydocs;

    // version notices
    $('#version').html(jquerydocs.version);
    document.title += ' v' + jquerydocs.version; // note - I have to manually insert the version since it's lacking :-(

    // now manually load the plugins API - I'm avoiding using loadDocs because it triggers a callback to api-load-complete
    // which causes our infinite loop :-(
    $.ajax({
        url: 'lib/docs/plugins.xml', // generated from jquery source: /tools/wikiapi2xml/createjQueryXMLDocs.py
        dataType: 'xml',
        async : false,
        success: parse
    });

    attachFind(jquerydocs);

    if (window.location.search) {
        $('#query').val(window.location.search.substr(1)).keyup();
    }

    init();
});

$.fn.stackSelect = function (fn) {
    return this.length ? this.animate({
        height: 0,
        opacity: 0,
        padding: 0
    }, 200, function () {
        $(this).hide();
        fn.call(this);
    }) : fn.call(this);
};

function loadFavs() {
    if (!airapp) {
        // read favs from cookie
        eval('favs = ' + readCookie('favs'));// need to change to Crockford's json parser
    } else {
        var FileSystem = window.runtime.flash.filesystem;

        var file = FileSystem.File.applicationStorageDirectory.resolvePath('favs.json');
        if (file.exists) {
            var fileStream = new FileSystem.FileStream();
            fileStream.open(file, FileSystem.FileMode.READ);
            var data = fileStream.readMultiByte(file.size, FileSystem.File.systemCharset);
            fileStream.close();

            favs = JSON.parse(data);
        }
    }

    if (favs == null) {
        favs = {};
    } else {
        for (var k in favs) {
            jquerydocs.data[k].fav = 'true';
        }
    }
}

function loadCategories(root) {
    var html = [];
    var categories = jquerydocs.categories;
    root = (root || "").toLowerCase();

    if (root) {
        lastRoot = root;
    } else {
        root = lastRoot;
    }

    for (var i = 0; i < categories.length; i++) {
        if (categories[i].root == root) {
            html.push('<dt id="' + i + '">' + categories[i].name.replace(/^UI\//, '') + '</dt>');
        }
    }

    if (html.length) {
        $('#browser > div').html('<dl id="categories">' + html.join('') + '</dl>');
    } else {
        $('#browser > div').html('<p class="loading">No functions found.</p>');
    }
}

// TODO find out why it's not in id order
function showFunctions(items, $wrapper) {
    var argsA = [],
            args,
            html = [],
            i, j;

    $wrapper.find('> #functions, dd').remove();

    for (i = 0; i < items.length; i++) {
        argsA = [];
        args = '';

        if (items[i].params.length) {
            for (j = 0; j < items[i].params.length; j++) {
                argsA.push(items[i].params[j].name);
            }
            args = '(' + argsA.join(', ') + ')';
        } else if (items[i].type == 'function') {
            args = '()';
        }

        html.push('<dt id="' + items[i].searchname + items[i].id + '" class="direct">' + items[i].name + args
                + '</dt>');
    }

    step = (200 * $wrapper.find('dl').length) + 10;
    var $html = $('<dl id="functions" class="absolute" style="left: ' + step + 'px;">' + html.join('')
            + '</dl>').css('display', 'none');
    $wrapper.append($html);
}


function init() {
    var $wrapper = $('#browser > div'),
            $categories = $('#categories'),
            $detail = $('#detail > div'),
            blank_iframe = 'index-blank.html',
            title = document.title,
            html = [],
            categories = jquerydocs.categories;

    loadFavs();
    loadCategories();

    var $nav = $('#navigation a').click(function () {
        $nav.removeClass('selected');
        $(this).addClass('selected');

        $('#query').val('');

        if (this.hash.substr(1) == 'favs') {
            $wrapper.empty();
            var items = jquerydocs.find('true', 'fav');
            showFunctions(items, $wrapper);
            if (items.length) {
                $('#functions').show();
            } else {
                $('#browser > div').html('<p class="loading">No functions found.</p>');
            }
        } else {
            loadCategories(this.hash.substr(1));
        }

        return false;
    });

    $wrapper.click(function (event) {
        categories = jquerydocs.categories;
        var dt = (event.target.nodeName == 'DT'),
                $selected = $(event.target),
                step = 210,
                i, j, // loop indices
                q = $selected.text(), // search term
                items, // matches
                item, // specific function
                argsA = [],
                args = '',
                html = [],
                deselect = false,
                category,
                subcategory,
                fns = [];

        if (dt) {

            var inId = $selected.parent().attr('id');

            if ($selected.is('.active') && $selected.parent('#functions').length == 0) {
                // go up a level
                deselect = true;

                if ($selected.parent('#subcategories').length) {
                    $selected = $('#categories').find('.active');
                } else {
                    $('#functions, #subcategories').slideUp(function () {
                        $(this).remove();
                    });
                    $('#categories').find('dt').removeClass('active').css('padding', 5).animate({
                        opacity : 1,
                        height : '100%'
                    });
                    return;
                }
            } else {
                $selected.parent().find('dt').removeClass('active');
                $selected.addClass('active');
            }

            if ($selected.parents('#categories').length) { // category selected
                category = $selected.attr('id');

                $categories.find('dt').removeClass('active');
                $selected.addClass('active');
                $wrapper.find('> dl:not(#categories), dd').remove();

                if (jquerydocs.categories[category].subcategories
                        && jquerydocs.categories[category].subcategories.length) {
                    for (i = 0; i < categories[category].subcategories.length; i++) {
                        html.push('<dt id="subcategory' + i + '">' + categories[category].subcategories[i].name
                                + '</dt>');
                    }

                    var $html = $('<dl id="subcategories" class="absolute">' + html.join('') + '</dl>');
                    $html.css('display', 'none');

                    $wrapper.append($html);
                    $wrapper.find('#categories dt:not(.active)').stackSelect(function () {
                        $wrapper.find('#subcategories').slideDown();
                    });
                }
            } else if ($selected.parents('#subcategories').length) { // subcategory selected
                category = $('#categories .active').attr('id');
                subcategory = $selected.attr('id').replace(/subcategory/, '');
                fns = [];
                for (i = 0; i < jquerydocs.categories[category].subcategories[subcategory].functions.length; i++) {
                    fns.push(jquerydocs.categories[category].subcategories[subcategory].functions[i]);
                }

                showFunctions(jquerydocs.find(fns), $wrapper); // function because we get reused.

                $wrapper.find('#subcategories dt:not(.active)').stackSelect(function () {
                    $wrapper.find('#functions').slideDown();
                });
            } else if ($selected.parents('#functions').length) { // function selected
                item = jquerydocs.data[$selected.attr('id')];

                $detail.empty();

                document.title = q + ' :: ' + title;

                html.push('<h1>' + q + ' <span class="type">' + item.type + '</span> <span class="fav' + (item.fav
                        ? ' on' : '') + '" id="' + item.name + item.id + '"></span></h1>');
                html.push('<p class="meta">Category: ' + item.category + '/' + item.subcategory + (item.added
                        ? ' (added ' + item.added + ')' : '') + '</p>');
                html.push(item.longdesc || '<p>' + item.desc + '</p>'); // longdesc is usally HTML
                html.push('<h2>Returns</h2>');
                html.push('<p>' + (item['return'] || 'Nothing') + '</p>');

                if (item.params.length) {
                    html.push('<h2>Parameters</h2>');
                    html.push('<ul class="options">');
                    for (i = 0; i < item.params.length; i++) {
                        html.push('<li><strong>' + item.params[i].name + '</strong> ');
                        if (item.params[i].type) {
                            html.push('(' + item.params[i].type + ')');
                        }
                        html.push(': ' + item.params[i].desc + '</li>');
                    }
                    html.push('</ul>');
                }

                // detailed options
                if (item.options && item.options.length) {
                    html.push('<h2>Options</h2>');
                    html.push('<ul class="options">');
                    for (i = 0; i < item.options.length; i++) {
                        html.push('<li><strong>' + item.options[i].name + '</strong> ');
                        if (item.options[i].type) {
                            html.push('(' + item.options[i].type + ')');
                        }
                        html.push(': ' + item.options[i].desc + '</li>');
                    }
                    html.push('</ul>');

                }

                for (i = 0; i < item.examples.length; i++) {
                    html.push('<h2>Example</h2>');
                    if (item.examples[i].desc) {
                        html.push('<p>' + item.examples[i].desc + '</p>');
                    }
                    html.push('<h3>jQuery Code</h3>');
                    html.push('<pre>' + item.examples[i].htmlCode + '</pre>');

                    // this is special code that will convert in to a real running example once run through 'runExample(item)'
                    if (item.examples[i].html) {

                        // prepare for a JSBin link - currently point to live
                        j = item.examples[i].runCode.replace("<script>\n" + item.examples[i].code
                                + "\n</script>", function () {
                            return "<script type=\"text/javascript\">\n%code%\n</script>";
                        });

                        html.push('<div class="liveExample" style="position: relative;"><a class="edit external" target="_blank" href="http://jsbin.com/?html='
                                + encodeURIComponent(j.replace(apiloader.example_jquery.offline, apiloader.example_jquery.offline))
                                + '&js=' + encodeURIComponent(item.examples[i].code)
                                + '" title="Edit and run this code">edit</a><iframe sandboxRoot="http://localhost/" documentRoot="app:/" id="'
                                + item.examples[i].id + '" class="example" src="' + blank_iframe + '"></iframe></div>');
                    }
                }

                // step = (200 * $wrapper.find('dl').length) + 10;
                $detail.append('<dd id="function" class="text absolute">' + html.join('') + '</dd>');
                $detail.parent().get(0).scrollTop = 0; // reset the overflow position
                fixLinks($detail.find('dd')); // makes links to more docs absolute rather than relative (should do in the api-docs.js)

                // in place of runExamples to allow AIR to run the iframe's contents
                if (airapp) {
                    $('div.liveExample a.external').click(function () {
                        window.runtime.flash.net.navigateToURL(new window.runtime.flash.net.URLRequest(this.href));
                        return false;
                    });

                    for (i = 0; i < item.examples.length; i++) {
                        win = document.getElementById(item.examples[i].id);
                        if (win) { // some examples don't have code
                            $(win).bind('load', { example: item.examples[i] }, function (event) {
                                var example = event.data.example;
                                this.contentWindow.childSandboxBridge.write(
                                        example.runCode.replace("</head>", '<base href="http://docs.jquery.com" /><style>html,body{border:0; margin:0; padding:0;}</style></head>'));
                            });
                        }
                    }
                } else {
                    runExample(item);
                }

                // code highlight
                prettyPrint($detail.get(0));
            }
        }
    });
}

(function () {

    if (!window.jquerydocs) {
        window.jquerydocs = {};
    } else {
        // we've already been included, bomb out
        return;
    }

    window.apiloader = {};

    var msie = /*@cc_on!@*/0;
    apiloader.blank_iframe = msie ? 'index-blank-ie.html' : 'index-blank.html';
    apiloader.online = window.runtime ? false : true;
    apiloader.example_jquery = {
        'online' : 'http://code.jquery.com/jquery-1.3.js',
        'offline' : 'http://code.jquery.com/jquery-1.3.js' //app:/lib/jquery/jquery-1.3.js'
    };

    var guid = 0;

    var re_opt = /options/i;

    init();

    window.loadDocs = function(data, async) {
        $(document).trigger('api-loading');

        if (typeof async == 'undefined') {
            async = true;
        }

        if (typeof data != 'undefined' && typeof data != 'string') {
            jquerydocs = data;
            attachFind(jquerydocs);
            $(document).trigger('api-load-success');
            $(document).trigger('api-load-complete');
        } else {
            // parser
            $.ajax({
                url: data
                        || 'jquery-docs.xml', // generated from jquery source: /tools/wikiapi2xml/createjQueryXMLDocs.py
                dataType: 'xml',
                success: parse,
                async: async,
                error: function () {
                    attachFind(jquerydocs);
                    $(document).trigger('api-load-error');
                },
                complete: function () {
                    $(document).trigger('api-load-complete');
                }
            });
        }
    };

    function init() {
        jquerydocs.letters = [];

        jquerydocs.data = {};
        jquerydocs.searchNames = [];
        jquerydocs.categories = [];
    }

    window.parse = function(xml) {
        var docinfo = $('docs', xml);

        if (!jquerydocs.startdoc) { // only initialise this once
            jquerydocs.startdoc = docinfo.attr('startdoc');
            jquerydocs.version = docinfo.attr('version');
            jquerydocs.timestamp = docinfo.attr('timestamp');
        }

        var letters = jquerydocs.letters; // holder before sorting and inserting

        var dict = getDictionary();
        var root = docinfo.attr('startdoc').toLowerCase();

        // loop through all types collecting data
        $('cat', xml).each(function (i) {
            var catName = this.getAttribute('value');
            var category = {};
            category.name = catName;
            category.root = root;
            category.subcategories = [];

            $('subcat', this).each(function (i) {
                var subcatName = this.getAttribute('value');
                var subcategory = { name : subcatName, functions : [] };

                $('function,property,selector', this).each(function () {
                    var data = {};
                    guid++;

                    // some function names have spaces around them - so trim
                    var name = this.getAttribute('name').replace(/^\s+|\s+$/g, '');

                    var searchName = name.toLowerCase().replace(/^jquery\./, '');
                    letters.push(name.toLowerCase().substr(0, 1));

                    name = name.replace(/^jquery\./i, '$.');

                    jquerydocs.searchNames.push(searchName + guid);
                    subcategory.functions.push(searchName + guid);

                    data['id'] = guid;
                    data['searchname'] = searchName;
                    data['name'] = name;
                    data['type'] = this.nodeName.toLowerCase();
                    data['category'] = this.getAttribute('cat');
                    data['subcategory'] = subcatName;
                    data['return'] = escapeHTML(this.getAttribute('return'));
                    data['added'] = $('added', this).text();
                    data['sample'] = $('> sample', this).text();
                    data['desc'] = $('> desc', this).text();
                    data['longdesc'] = deWikify($('> longdesc', this).text());

                    // silly hack because of conversion issue from wiki to text (the .ready function
                    // has HTML in the description), but also includes HTML that should be printed,
                    // in particular the body tag :-(
                    data.longdesc = data.longdesc.replace(/<body>/, '&lt;body&gt;');

                    // some descs are in HTML format, some aren't
                    if (!(/<p>/).test(data.longdesc)) {
                        data.longdesc = '<p>' + data.longdesc.split(/\n\n/).join('</p><p>') + '</p>';
                    }

                    // strip our empty p tag if there was no description
                    if (data.longdesc == '<p></p>') {
                        data.longdesc = '';
                    }

                    /** params - we'll also search for Options to decide whether we need to parse */
                    var readOptions = false;
                    data.params = [];
                    $('params', this).each(function (i) {
                        var type = escapeHTML(this.getAttribute('type'));
                        var name = this.getAttribute('name');
                        var opt = this.getAttribute('optional') || "";
                        var desc = $('desc', this).text();

                        if (re_opt.test(type)) {
                            readOptions = true;
                        }

                        data.params.push({
                            optional : (/true/i).test(opt), // bool
                            name : name,
                            type : type,
                            desc : desc
                        });
                    });

                    if (readOptions) {
                        data.options = [];
                        $('option', this).each(function () {
                            var option = {};
                            option['name'] = this.getAttribute('name');
                            option['default'] = this.getAttribute('default') || '';
                            option['type'] = escapeHTML(this.getAttribute('type'));
                            option['desc'] = deWikify($('desc', this).text());

                            data.options.push(option);
                        });
                    }

                    data.examples = [];
                    /** examples */
                    $('example', this).each(function (i) {
                        var iframe = '', exampleId = '';
                        var example = {};

                        example['code'] = $('code', this).text();
                        example['htmlCode'] = escapeHTML(example.code);
                        example['desc'] = deWikify(escapeHTML($('desc', this).text()));
                        example['css'] = $('css', this).text() || '';
                        example['inhead'] = $('inhead', this).text() || '';
                        example['html'] = $('html', this).text() || '';

                        exampleId = guid + 'iframeExample' + i;
                        example['id'] = exampleId;

                        if (example.html) {

                            iframe = '<iframe id="' + exampleId + '" class="example" src="' + apiloader.blank_iframe
                                    + '"></iframe>';

                            // we're storing the example iframe source to insert in to
                            // the iframe only once it's inserted in to the DOM.
                            example['runCode'] = iframeTemplate().replace(/%([a-z]*)%/ig, function (m, l) {
                                return example[l] || "";
                            });
                        } else {
                            example.runCode = '';
                        }

                        data.examples.push(example);
                    });

                    jquerydocs.data[searchName + data.id] = data;
                });

                category.subcategories.push(subcategory);
            });

            jquerydocs.categories.push(category); // FIXME should I warn if this exists?
        });

        jquerydocs.letters = unique($.map(letters.sort(), function (i) {
            return i.substr(0, 1);
        }));

        // attachFind(jquerydocs);

        $(document).trigger('api-load-success');
    };

    // helpers

    window.attachFind = function(o) {
        var categoryRootLookup = {};
        var i = jquerydocs.categories.length;
        while (i--) {
            categoryRootLookup[jquerydocs.categories[i].name] = jquerydocs.categories[i].root;
        }

        o.find = function (s, by) {
            var found = [],
                    tmp = {},
                    tmpNames = [],
                    lettersLK = {},
                    letters = [],
                    catsLK = {},
                    cats = [],
                    catPointer = 0,
                    subLK = {},
                    sub = [],
                    subcatPointer = 0;
            data = {}; // reusing i from above

            function add(data, i) {
                mapFields(data);

                tmp[jquerydocs.searchNames[i]] = data;
                tmpNames.push(jquerydocs.searchNames[i]);

                if (!lettersLK[jquerydocs.searchNames[i].substr(0, 1)]) {
                    lettersLK[jquerydocs.searchNames[i].substr(0, 1)] = true;
                    letters.push(jquerydocs.searchNames[i].substr(0, 1));
                }

                if (typeof catsLK[data.category] == 'undefined') {
                    catsLK[data.category] = catPointer;
                    cats.push({ name : data.category, subcategories : [], root : categoryRootLookup[data.category] });

                    subLK[data.category] = {};

                    subcatPointer = 0;
                    catPointer++;
                }

                if (typeof subLK[data.category][data.subcategory] == 'undefined') {
                    subLK[data.category][data.subcategory] = subcatPointer;
                    cats[catsLK[data.category]].subcategories.push({ name : data.subcategory, functions : [] });

                    subcatPointer++;
                }

                // bug here...
                cats[catsLK[data.category]].subcategories[subLK[data.category][data.subcategory]].functions.push(jquerydocs.searchNames[i]);
            }

            if (typeof s == 'string') {
                s = s.toLowerCase();
                by = (by || 'searchname').toLowerCase();

                if (by == 'name') by = 'searchname'; // search without the $.

                for (i = 0; i < jquerydocs.searchNames.length; i++) {
                    if (jquerydocs.data[jquerydocs.searchNames[i]][by]
                            && jquerydocs.data[jquerydocs.searchNames[i]][by].toLowerCase().indexOf(s) == 0) {
                        add(jquerydocs.data[jquerydocs.searchNames[i]], i);
                    }
                }
            } else if (s instanceof Array) {
                for (i = 0; i < s.length; i++) {
                    if (jquerydocs.data[s[i]]) {
                        add(jquerydocs.data[s[i]], i);
                    }
                }
            }

            tmpNames = tmpNames.sort().reverse(); // never sure if this is faster with the reverse
            i = tmpNames.length;
            while (i--) {
                found.push(tmp[tmpNames[i]]);
            }

            // this is kind of noddy, but returns the same object as we queried - which is cool!
            found.letters = letters;
            found.categories = cats;
            found.data = tmp;
            found.searchNames = tmpNames;
            attachFind(found);

            return found;
        };
    };

    function mapFields(data, dict) {
        if (data.fieldsMapped) {
            return;
        }

        var key, type;

        if (!dict) {
            dict = getDictionary();
        }

        for (key in dict) {
            type = typeof data[key];
            if (type != 'undefined') {
                if (type == 'object') {
                    mapFields(data[key], dict);
                }

                data[dict[key]] = data[key];
            }
        }
    }

    function unique(a) {
        var ret = [], done = {};

        try {
            for (var i = 0, length = a.length; i < length; i++) {
                var id = a[ i ];

                if (!done[ id ]) {
                    done[ id ] = true;
                    ret.push(a[ i ]);
                }
            }

        } catch(e) {
            ret = a;
        }

        return ret;
    }

    function iframeTemplate() {
        // array so that we maintain some formatting
        return [
            '<!' + 'DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"',
            '    "http://www.w3.org/TR/html4/loose.dtd">',
            '<' + 'html>',
            '<' + 'head>',
            '<base href="http://docs.jquery.com" />',
            '<' + 'script src="' + apiloader.example_jquery[apiloader.online ? 'online' : 'offline'] + '"><'
                    + '/script>',
            '%inhead%',
            '<' + 'style>',
            '%css%',
            '<' + '/style>',
            '<' + '/head>',
            '<' + 'body>',
            '%html%',
            '<' + 'script>',
            '$(document).ready(function(){', '%code%', '  });',
            '<' + '/script>',
            '<' + '/body>',
            '<' + '/html>'
        ].join("\n");
    }

    function getDictionary() {
        return {
            'a' : 'id',
            'b' : 'searchname',
            'c' : 'name',
            'd' : 'type',
            'e' : 'category',
            'f' : 'subcategory',
            'g' : 'return',
            'h' : 'added',
            'i' : 'sample',
            'j' : 'desc',
            'k' : 'longdesc',
            'l' : 'params',
            'm' : 'optional',
            'n' : 'options',
            'o' : 'default',
            'p' : 'examples',
            'q' : 'code',
            'r' : 'htmlCode',
            's' : 'css',
            't' : 'inhead',
            'u' : 'html',
            'v' : 'runCode'
        };
    }

    /** public utility functions */

    window.escapeHTML = function (s) {
        // converts null to string
        return (s + "").replace(/[<>]/g, function (m) {
            if (m == '<') return '&lt;';
            else if (m == '>') return '&gt;';
        });
    };

    window.cleanSelector = function(s) {
        return (s + "").replace(/[\$\.]/g, function (m) {
            // handle escaping characters that break the selector engine
            if (m == '$') {
                return '\\$';
            } else if (m == '.') {
                return '\\.';
            }
        });
    };

    window.linkifyTypes = function(type) {
        // cheeky way to avoid doing a massive if (m == x || m == y || m == etc) - we just do an .indexOf()
        var nodocs = '|jQuery|XMLHttpRequest|Plugins|Validator|Validation|undefined|or|Any|DOM|Map|top|left|lt|gt|\(s\)||'; // note we purposely include an empty match

        return type ? $.map(type.replace(/DOMElement/g, 'DOM Element').split(/, /), function (n) {
            // match words and linkify, then italic to the optionals
            return n.replace(/boolean/, 'Boolean').replace(/\b[a-z]*\b/gi, function (m, l) {
                // special case
                if (m == 'Elements') {
                    return '<a href="http://docs.jquery.com/Types#Element">Element</a>s';
                    // no specific documentation for these types
                } else if (nodocs.indexOf('|' + m + '|') !== -1) {
                    return m;
                } else {
                    return '<a href="http://docs.jquery.com/Types#' + m + '">' + m + '</a>';
                }
            });
        }).join(', ') : "";
    };

    window.deWikify = function (s) {
        return ("" + s).replace(/'''.*?'''/g, function (m) {
            return '<strong>' + m.replace(/'''/g, '') + '</strong>';
        }).replace(/''.*?''/g, function (m) {
            return '<em>' + m.replace(/''/g, '') + '</em>';
        }).replace(/\[http.*?\]/, function (m) {
            var p = m.replace(/^\[/, '').replace(/\]$/, '').split(/ /);
            return '<a href="' + p[0] + '">' + (p.length == 2 ? p[1] : p[0]) + '</a>';
        }).replace(/(((^|\n)(\*|[0-9]+.).*)+)/g, function (m) {
            var type = 'ol';
            // strip leading new line
            m = m.replace(/^\s+|\s+$/g, "");
            if (m.match(/^\*/)) type = 'ul';
            return '<' + type + '><li>' + m.replace(/\*?/g, '').split(/\n/).join("</li><li>") + '</li></' + type + '>';
        });
    };

    window.runExample = function(data) {
        if (!data.examples || data.examples.length == 0) return;

        var i, win, example;

        for (i = 0; i < data.examples.length; i++) {
            example = data.examples[i];

            win = $('#' + cleanSelector(example.id)).get(0);
            if (win) {
                win = win.contentDocument || win.contentWindow.document;
                if (msie) {

                }
                // from docs.jquery.com
                win.write(example.runCode
                        .replace("$(document).ready(function(){", "window.onload = (function(){try{")
                        .replace(/}\);\s*<\/sc/, "}catch(e){}});</sc")
                        .replace("</head>", "<style>html,body{border:0; margin:0; padding:0;}</style></head>"));

                win.close();
            }
        }
    };

    window.fixLinks = function (context) {
        // since the source comes from the wiki, we need to adjust some of the links
        $('a', context).each(function () {
            var href = this.getAttribute('href');
            if (href && !href.match(/http/) && !href.match(/^#/) && this.className != 'fnName') {
                this.host = 'docs.jquery.com';
                this.pathname = this.pathname.replace(window.location.pathname, '');
            }
        });
    };

})();