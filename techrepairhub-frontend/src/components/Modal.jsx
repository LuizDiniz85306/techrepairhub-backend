export default function Modal({ title, children, onClose, footer }) {
  return (
    <div className="modal-backdrop" role="presentation">
      <section className="modal-card" role="dialog" aria-modal="true" aria-label={title || "Modal"}>
        <div className="modal-header">
          {title && <h2>{title}</h2>}
          <button type="button" onClick={onClose} aria-label="Fechar">x</button>
        </div>
        <div className="modal-body">{children}</div>
        {footer && <div className="modal-footer">{footer}</div>}
      </section>
    </div>
  );
}
