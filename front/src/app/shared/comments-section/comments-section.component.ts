import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { CommentsService, Comment } from 'src/app/services/comments.service';

@Component({
  selector: 'app-comments-section',
  templateUrl: './comments-section.component.html',
  styleUrls: ['./comments-section.component.scss']
})
export class CommentsSectionComponent implements OnInit {
  @Input() articleId!: number;

  comments: Comment[] = [];
  commentControl = new FormControl('', [Validators.required]);

  constructor(private commentsService: CommentsService) {}

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments(): void {
    this.commentsService.getComments(this.articleId).subscribe((data) => {
      this.comments = data;
    });
  }

  submitComment(): void {
    if (this.commentControl.invalid) return;

    const content = this.commentControl.value!;
    this.commentsService.addComment(this.articleId, content).subscribe({
      next: (comment) => {
        this.comments.unshift(comment);
        this.commentControl.reset();
      },
      error: () => {
        alert('Erreur lors de l\'ajout du commentaire.');
      }
    });
  }

}
