// Compiled by ClojureScript 1.11.132 {:optimizations :none}
goog.provide('cljs.repl');
goog.require('cljs.core');
goog.require('cljs.spec.alpha');
goog.require('goog.string');
goog.require('goog.string.format');
cljs.repl.print_doc = (function cljs$repl$print_doc(p__11053){
var map__11054 = p__11053;
var map__11054__$1 = cljs.core.__destructure_map.call(null,map__11054);
var m = map__11054__$1;
var n = cljs.core.get.call(null,map__11054__$1,new cljs.core.Keyword(null,"ns","ns",441598760));
var nm = cljs.core.get.call(null,map__11054__$1,new cljs.core.Keyword(null,"name","name",1843675177));
cljs.core.println.call(null,"-------------------------");

cljs.core.println.call(null,(function (){var or__5002__auto__ = new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return [(function (){var temp__5804__auto__ = new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(temp__5804__auto__)){
var ns = temp__5804__auto__;
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(ns),"/"].join('');
} else {
return null;
}
})(),cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join('');
}
})());

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Protocol");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m))){
var seq__11055_11083 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__11056_11084 = null;
var count__11057_11085 = (0);
var i__11058_11086 = (0);
while(true){
if((i__11058_11086 < count__11057_11085)){
var f_11087 = cljs.core._nth.call(null,chunk__11056_11084,i__11058_11086);
cljs.core.println.call(null,"  ",f_11087);


var G__11088 = seq__11055_11083;
var G__11089 = chunk__11056_11084;
var G__11090 = count__11057_11085;
var G__11091 = (i__11058_11086 + (1));
seq__11055_11083 = G__11088;
chunk__11056_11084 = G__11089;
count__11057_11085 = G__11090;
i__11058_11086 = G__11091;
continue;
} else {
var temp__5804__auto___11092 = cljs.core.seq.call(null,seq__11055_11083);
if(temp__5804__auto___11092){
var seq__11055_11093__$1 = temp__5804__auto___11092;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__11055_11093__$1)){
var c__5525__auto___11094 = cljs.core.chunk_first.call(null,seq__11055_11093__$1);
var G__11095 = cljs.core.chunk_rest.call(null,seq__11055_11093__$1);
var G__11096 = c__5525__auto___11094;
var G__11097 = cljs.core.count.call(null,c__5525__auto___11094);
var G__11098 = (0);
seq__11055_11083 = G__11095;
chunk__11056_11084 = G__11096;
count__11057_11085 = G__11097;
i__11058_11086 = G__11098;
continue;
} else {
var f_11099 = cljs.core.first.call(null,seq__11055_11093__$1);
cljs.core.println.call(null,"  ",f_11099);


var G__11100 = cljs.core.next.call(null,seq__11055_11093__$1);
var G__11101 = null;
var G__11102 = (0);
var G__11103 = (0);
seq__11055_11083 = G__11100;
chunk__11056_11084 = G__11101;
count__11057_11085 = G__11102;
i__11058_11086 = G__11103;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_11104 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__5002__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.call(null,arglists_11104);
} else {
cljs.core.prn.call(null,((cljs.core._EQ_.call(null,new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first.call(null,arglists_11104)))?cljs.core.second.call(null,arglists_11104):arglists_11104));
}
} else {
}
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"special-form","special-form",-1326536374).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Special Form");

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.contains_QMARK_.call(null,m,new cljs.core.Keyword(null,"url","url",276297046))){
if(cljs.core.truth_(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))){
return cljs.core.println.call(null,["\n  Please see http://clojure.org/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))].join(''));
} else {
return null;
}
} else {
return cljs.core.println.call(null,["\n  Please see http://clojure.org/special_forms#",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Macro");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Spec");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"REPL Special Function");
} else {
}

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
var seq__11059_11105 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__11060_11106 = null;
var count__11061_11107 = (0);
var i__11062_11108 = (0);
while(true){
if((i__11062_11108 < count__11061_11107)){
var vec__11071_11109 = cljs.core._nth.call(null,chunk__11060_11106,i__11062_11108);
var name_11110 = cljs.core.nth.call(null,vec__11071_11109,(0),null);
var map__11074_11111 = cljs.core.nth.call(null,vec__11071_11109,(1),null);
var map__11074_11112__$1 = cljs.core.__destructure_map.call(null,map__11074_11111);
var doc_11113 = cljs.core.get.call(null,map__11074_11112__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_11114 = cljs.core.get.call(null,map__11074_11112__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name_11110);

cljs.core.println.call(null," ",arglists_11114);

if(cljs.core.truth_(doc_11113)){
cljs.core.println.call(null," ",doc_11113);
} else {
}


var G__11115 = seq__11059_11105;
var G__11116 = chunk__11060_11106;
var G__11117 = count__11061_11107;
var G__11118 = (i__11062_11108 + (1));
seq__11059_11105 = G__11115;
chunk__11060_11106 = G__11116;
count__11061_11107 = G__11117;
i__11062_11108 = G__11118;
continue;
} else {
var temp__5804__auto___11119 = cljs.core.seq.call(null,seq__11059_11105);
if(temp__5804__auto___11119){
var seq__11059_11120__$1 = temp__5804__auto___11119;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__11059_11120__$1)){
var c__5525__auto___11121 = cljs.core.chunk_first.call(null,seq__11059_11120__$1);
var G__11122 = cljs.core.chunk_rest.call(null,seq__11059_11120__$1);
var G__11123 = c__5525__auto___11121;
var G__11124 = cljs.core.count.call(null,c__5525__auto___11121);
var G__11125 = (0);
seq__11059_11105 = G__11122;
chunk__11060_11106 = G__11123;
count__11061_11107 = G__11124;
i__11062_11108 = G__11125;
continue;
} else {
var vec__11075_11126 = cljs.core.first.call(null,seq__11059_11120__$1);
var name_11127 = cljs.core.nth.call(null,vec__11075_11126,(0),null);
var map__11078_11128 = cljs.core.nth.call(null,vec__11075_11126,(1),null);
var map__11078_11129__$1 = cljs.core.__destructure_map.call(null,map__11078_11128);
var doc_11130 = cljs.core.get.call(null,map__11078_11129__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_11131 = cljs.core.get.call(null,map__11078_11129__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name_11127);

cljs.core.println.call(null," ",arglists_11131);

if(cljs.core.truth_(doc_11130)){
cljs.core.println.call(null," ",doc_11130);
} else {
}


var G__11132 = cljs.core.next.call(null,seq__11059_11120__$1);
var G__11133 = null;
var G__11134 = (0);
var G__11135 = (0);
seq__11059_11105 = G__11132;
chunk__11060_11106 = G__11133;
count__11061_11107 = G__11134;
i__11062_11108 = G__11135;
continue;
}
} else {
}
}
break;
}
} else {
}

if(cljs.core.truth_(n)){
var temp__5804__auto__ = cljs.spec.alpha.get_spec.call(null,cljs.core.symbol.call(null,cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.ns_name.call(null,n)),cljs.core.name.call(null,nm)));
if(cljs.core.truth_(temp__5804__auto__)){
var fnspec = temp__5804__auto__;
cljs.core.print.call(null,"Spec");

var seq__11079 = cljs.core.seq.call(null,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"args","args",1315556576),new cljs.core.Keyword(null,"ret","ret",-468222814),new cljs.core.Keyword(null,"fn","fn",-1175266204)], null));
var chunk__11080 = null;
var count__11081 = (0);
var i__11082 = (0);
while(true){
if((i__11082 < count__11081)){
var role = cljs.core._nth.call(null,chunk__11080,i__11082);
var temp__5804__auto___11136__$1 = cljs.core.get.call(null,fnspec,role);
if(cljs.core.truth_(temp__5804__auto___11136__$1)){
var spec_11137 = temp__5804__auto___11136__$1;
cljs.core.print.call(null,["\n ",cljs.core.name.call(null,role),":"].join(''),cljs.spec.alpha.describe.call(null,spec_11137));
} else {
}


var G__11138 = seq__11079;
var G__11139 = chunk__11080;
var G__11140 = count__11081;
var G__11141 = (i__11082 + (1));
seq__11079 = G__11138;
chunk__11080 = G__11139;
count__11081 = G__11140;
i__11082 = G__11141;
continue;
} else {
var temp__5804__auto____$1 = cljs.core.seq.call(null,seq__11079);
if(temp__5804__auto____$1){
var seq__11079__$1 = temp__5804__auto____$1;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__11079__$1)){
var c__5525__auto__ = cljs.core.chunk_first.call(null,seq__11079__$1);
var G__11142 = cljs.core.chunk_rest.call(null,seq__11079__$1);
var G__11143 = c__5525__auto__;
var G__11144 = cljs.core.count.call(null,c__5525__auto__);
var G__11145 = (0);
seq__11079 = G__11142;
chunk__11080 = G__11143;
count__11081 = G__11144;
i__11082 = G__11145;
continue;
} else {
var role = cljs.core.first.call(null,seq__11079__$1);
var temp__5804__auto___11146__$2 = cljs.core.get.call(null,fnspec,role);
if(cljs.core.truth_(temp__5804__auto___11146__$2)){
var spec_11147 = temp__5804__auto___11146__$2;
cljs.core.print.call(null,["\n ",cljs.core.name.call(null,role),":"].join(''),cljs.spec.alpha.describe.call(null,spec_11147));
} else {
}


var G__11148 = cljs.core.next.call(null,seq__11079__$1);
var G__11149 = null;
var G__11150 = (0);
var G__11151 = (0);
seq__11079 = G__11148;
chunk__11080 = G__11149;
count__11081 = G__11150;
i__11082 = G__11151;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
} else {
return null;
}
}
});
/**
 * Constructs a data representation for a Error with keys:
 *  :cause - root cause message
 *  :phase - error phase
 *  :via - cause chain, with cause keys:
 *           :type - exception class symbol
 *           :message - exception message
 *           :data - ex-data
 *           :at - top stack element
 *  :trace - root cause stack elements
 */
cljs.repl.Error__GT_map = (function cljs$repl$Error__GT_map(o){
return cljs.core.Throwable__GT_map.call(null,o);
});
/**
 * Returns an analysis of the phase, error, cause, and location of an error that occurred
 *   based on Throwable data, as returned by Throwable->map. All attributes other than phase
 *   are optional:
 *  :clojure.error/phase - keyword phase indicator, one of:
 *    :read-source :compile-syntax-check :compilation :macro-syntax-check :macroexpansion
 *    :execution :read-eval-result :print-eval-result
 *  :clojure.error/source - file name (no path)
 *  :clojure.error/line - integer line number
 *  :clojure.error/column - integer column number
 *  :clojure.error/symbol - symbol being expanded/compiled/invoked
 *  :clojure.error/class - cause exception class symbol
 *  :clojure.error/cause - cause exception message
 *  :clojure.error/spec - explain-data for spec error
 */
cljs.repl.ex_triage = (function cljs$repl$ex_triage(datafied_throwable){
var map__11154 = datafied_throwable;
var map__11154__$1 = cljs.core.__destructure_map.call(null,map__11154);
var via = cljs.core.get.call(null,map__11154__$1,new cljs.core.Keyword(null,"via","via",-1904457336));
var trace = cljs.core.get.call(null,map__11154__$1,new cljs.core.Keyword(null,"trace","trace",-1082747415));
var phase = cljs.core.get.call(null,map__11154__$1,new cljs.core.Keyword(null,"phase","phase",575722892),new cljs.core.Keyword(null,"execution","execution",253283524));
var map__11155 = cljs.core.last.call(null,via);
var map__11155__$1 = cljs.core.__destructure_map.call(null,map__11155);
var type = cljs.core.get.call(null,map__11155__$1,new cljs.core.Keyword(null,"type","type",1174270348));
var message = cljs.core.get.call(null,map__11155__$1,new cljs.core.Keyword(null,"message","message",-406056002));
var data = cljs.core.get.call(null,map__11155__$1,new cljs.core.Keyword(null,"data","data",-232669377));
var map__11156 = data;
var map__11156__$1 = cljs.core.__destructure_map.call(null,map__11156);
var problems = cljs.core.get.call(null,map__11156__$1,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814));
var fn = cljs.core.get.call(null,map__11156__$1,new cljs.core.Keyword("cljs.spec.alpha","fn","cljs.spec.alpha/fn",408600443));
var caller = cljs.core.get.call(null,map__11156__$1,new cljs.core.Keyword("cljs.spec.test.alpha","caller","cljs.spec.test.alpha/caller",-398302390));
var map__11157 = new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.first.call(null,via));
var map__11157__$1 = cljs.core.__destructure_map.call(null,map__11157);
var top_data = map__11157__$1;
var source = cljs.core.get.call(null,map__11157__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
return cljs.core.assoc.call(null,(function (){var G__11158 = phase;
var G__11158__$1 = (((G__11158 instanceof cljs.core.Keyword))?G__11158.fqn:null);
switch (G__11158__$1) {
case "read-source":
var map__11159 = data;
var map__11159__$1 = cljs.core.__destructure_map.call(null,map__11159);
var line = cljs.core.get.call(null,map__11159__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.call(null,map__11159__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var G__11160 = cljs.core.merge.call(null,new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.second.call(null,via)),top_data);
var G__11160__$1 = (cljs.core.truth_(source)?cljs.core.assoc.call(null,G__11160,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__11160);
var G__11160__$2 = (cljs.core.truth_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,source))?cljs.core.dissoc.call(null,G__11160__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__11160__$1);
if(cljs.core.truth_(message)){
return cljs.core.assoc.call(null,G__11160__$2,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__11160__$2;
}

break;
case "compile-syntax-check":
case "compilation":
case "macro-syntax-check":
case "macroexpansion":
var G__11161 = top_data;
var G__11161__$1 = (cljs.core.truth_(source)?cljs.core.assoc.call(null,G__11161,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__11161);
var G__11161__$2 = (cljs.core.truth_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,source))?cljs.core.dissoc.call(null,G__11161__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__11161__$1);
var G__11161__$3 = (cljs.core.truth_(type)?cljs.core.assoc.call(null,G__11161__$2,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__11161__$2);
var G__11161__$4 = (cljs.core.truth_(message)?cljs.core.assoc.call(null,G__11161__$3,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__11161__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.call(null,G__11161__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__11161__$4;
}

break;
case "read-eval-result":
case "print-eval-result":
var vec__11162 = cljs.core.first.call(null,trace);
var source__$1 = cljs.core.nth.call(null,vec__11162,(0),null);
var method = cljs.core.nth.call(null,vec__11162,(1),null);
var file = cljs.core.nth.call(null,vec__11162,(2),null);
var line = cljs.core.nth.call(null,vec__11162,(3),null);
var G__11165 = top_data;
var G__11165__$1 = (cljs.core.truth_(line)?cljs.core.assoc.call(null,G__11165,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),line):G__11165);
var G__11165__$2 = (cljs.core.truth_(file)?cljs.core.assoc.call(null,G__11165__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file):G__11165__$1);
var G__11165__$3 = (cljs.core.truth_((function (){var and__5000__auto__ = source__$1;
if(cljs.core.truth_(and__5000__auto__)){
return method;
} else {
return and__5000__auto__;
}
})())?cljs.core.assoc.call(null,G__11165__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null))):G__11165__$2);
var G__11165__$4 = (cljs.core.truth_(type)?cljs.core.assoc.call(null,G__11165__$3,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__11165__$3);
if(cljs.core.truth_(message)){
return cljs.core.assoc.call(null,G__11165__$4,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__11165__$4;
}

break;
case "execution":
var vec__11166 = cljs.core.first.call(null,trace);
var source__$1 = cljs.core.nth.call(null,vec__11166,(0),null);
var method = cljs.core.nth.call(null,vec__11166,(1),null);
var file = cljs.core.nth.call(null,vec__11166,(2),null);
var line = cljs.core.nth.call(null,vec__11166,(3),null);
var file__$1 = cljs.core.first.call(null,cljs.core.remove.call(null,(function (p1__11153_SHARP_){
var or__5002__auto__ = (p1__11153_SHARP_ == null);
if(or__5002__auto__){
return or__5002__auto__;
} else {
return new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,p1__11153_SHARP_);
}
}),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"file","file",-1269645878).cljs$core$IFn$_invoke$arity$1(caller),file], null)));
var err_line = (function (){var or__5002__auto__ = new cljs.core.Keyword(null,"line","line",212345235).cljs$core$IFn$_invoke$arity$1(caller);
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return line;
}
})();
var G__11169 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type], null);
var G__11169__$1 = (cljs.core.truth_(err_line)?cljs.core.assoc.call(null,G__11169,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),err_line):G__11169);
var G__11169__$2 = (cljs.core.truth_(message)?cljs.core.assoc.call(null,G__11169__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__11169__$1);
var G__11169__$3 = (cljs.core.truth_((function (){var or__5002__auto__ = fn;
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
var and__5000__auto__ = source__$1;
if(cljs.core.truth_(and__5000__auto__)){
return method;
} else {
return and__5000__auto__;
}
}
})())?cljs.core.assoc.call(null,G__11169__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(function (){var or__5002__auto__ = fn;
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return (new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null));
}
})()):G__11169__$2);
var G__11169__$4 = (cljs.core.truth_(file__$1)?cljs.core.assoc.call(null,G__11169__$3,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file__$1):G__11169__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.call(null,G__11169__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__11169__$4;
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__11158__$1)].join('')));

}
})(),new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358),phase);
});
/**
 * Returns a string from exception data, as produced by ex-triage.
 *   The first line summarizes the exception phase and location.
 *   The subsequent lines describe the cause.
 */
cljs.repl.ex_str = (function cljs$repl$ex_str(p__11173){
var map__11174 = p__11173;
var map__11174__$1 = cljs.core.__destructure_map.call(null,map__11174);
var triage_data = map__11174__$1;
var phase = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358));
var source = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
var line = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var symbol = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994));
var class$ = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890));
var cause = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742));
var spec = cljs.core.get.call(null,map__11174__$1,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595));
var loc = [cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__5002__auto__ = source;
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return "<cljs repl>";
}
})()),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__5002__auto__ = line;
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return (1);
}
})()),(cljs.core.truth_(column)?[":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column)].join(''):"")].join('');
var class_name = cljs.core.name.call(null,(function (){var or__5002__auto__ = class$;
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return "";
}
})());
var simple_class = class_name;
var cause_type = ((cljs.core.contains_QMARK_.call(null,new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["RuntimeException",null,"Exception",null], null), null),simple_class))?"":[" (",simple_class,")"].join(''));
var format = goog.string.format;
var G__11175 = phase;
var G__11175__$1 = (((G__11175 instanceof cljs.core.Keyword))?G__11175.fqn:null);
switch (G__11175__$1) {
case "read-source":
return format.call(null,"Syntax error reading source at (%s).\n%s\n",loc,cause);

break;
case "macro-syntax-check":
return format.call(null,"Syntax error macroexpanding %sat (%s).\n%s",(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,(cljs.core.truth_(spec)?(function (){var sb__5647__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__11176_11185 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__11177_11186 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__11178_11187 = true;
var _STAR_print_fn_STAR__temp_val__11179_11188 = (function (x__5648__auto__){
return sb__5647__auto__.append(x__5648__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__11178_11187);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__11179_11188);

try{cljs.spec.alpha.explain_out.call(null,cljs.core.update.call(null,spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.call(null,(function (p1__11171_SHARP_){
return cljs.core.dissoc.call(null,p1__11171_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__11177_11186);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__11176_11185);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5647__auto__);
})():format.call(null,"%s\n",cause)));

break;
case "macroexpansion":
return format.call(null,"Unexpected error%s macroexpanding %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "compile-syntax-check":
return format.call(null,"Syntax error%s compiling %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "compilation":
return format.call(null,"Unexpected error%s compiling %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "read-eval-result":
return format.call(null,"Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause);

break;
case "print-eval-result":
return format.call(null,"Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause);

break;
case "execution":
if(cljs.core.truth_(spec)){
return format.call(null,"Execution error - invalid arguments to %s at (%s).\n%s",symbol,loc,(function (){var sb__5647__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__11180_11189 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__11181_11190 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__11182_11191 = true;
var _STAR_print_fn_STAR__temp_val__11183_11192 = (function (x__5648__auto__){
return sb__5647__auto__.append(x__5648__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__11182_11191);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__11183_11192);

try{cljs.spec.alpha.explain_out.call(null,cljs.core.update.call(null,spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.call(null,(function (p1__11172_SHARP_){
return cljs.core.dissoc.call(null,p1__11172_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__11181_11190);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__11180_11189);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5647__auto__);
})());
} else {
return format.call(null,"Execution error%s at %s(%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__11175__$1)].join('')));

}
});
cljs.repl.error__GT_str = (function cljs$repl$error__GT_str(error){
return cljs.repl.ex_str.call(null,cljs.repl.ex_triage.call(null,cljs.repl.Error__GT_map.call(null,error)));
});

//# sourceMappingURL=repl.js.map
