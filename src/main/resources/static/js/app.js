function toggleTheme(){
 const current=document.documentElement.getAttribute('data-theme');
 const next=current==='dark'?'light':'dark';
 document.documentElement.setAttribute('data-theme',next);
 localStorage.setItem('orbit-theme',next);
}
(function(){
 const saved=localStorage.getItem('orbit-theme')||'light';
 document.documentElement.setAttribute('data-theme',saved);
})();
